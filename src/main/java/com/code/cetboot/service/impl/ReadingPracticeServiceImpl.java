package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.RecordType;
import com.code.cetboot.dto.ExerciseDTO;
import com.code.cetboot.dto.ReadingPracticeDTO;
import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.entity.ReadingPractice;
import com.code.cetboot.entity.ReadingRecord;
import com.code.cetboot.exception.ServiceException;
import com.code.cetboot.mapper.ExerciseMapper;
import com.code.cetboot.mapper.ReadingRecordMapper;
import com.code.cetboot.service.ExerciseRecordService;
import com.code.cetboot.service.ExerciseService;
import com.code.cetboot.service.ReadingPracticeService;
import com.code.cetboot.mapper.ReadingPracticeMapper;
import com.code.cetboot.vo.ExerciseVO;
import com.code.cetboot.vo.reading.ReadingPracticeVO;
import com.code.cetboot.vo.reading.ReadingRecordsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author 19735
* @description 针对表【reading_practice】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class ReadingPracticeServiceImpl extends ServiceImpl<ReadingPracticeMapper, ReadingPractice>
    implements ReadingPracticeService{

    @Resource
    private ExerciseMapper exerciseMapper;

    @Resource
    private ReadingRecordMapper readingRecordMapper;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @Resource
    private ExerciseService exerciseService;

    @Override
    public Result getPractices(Page<ReadingPractice> pageDto, String keyword) {
        Page<ReadingPractice> readingPracticePage = baseMapper.selectReadingPracticeByKeyword(pageDto, keyword);
        return Result.success("获取阅读练习题成功", PageResult.of(readingPracticePage));
    }

    @Override
    public Result getPractice(Integer practiceId) {
        ReadingPracticeVO readingPracticeVO = getReadingPracticeVO(false, practiceId);
        if (readingPracticeVO == null) {
            return Result.fail("获取阅读练习题失败, ID不存在");
        }
        return Result.success("获取阅读练习题成功", readingPracticeVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result submit(ReadingPracticeDTO readingPracticeDTO) {
        Integer practiceId = readingPracticeDTO.getReadingPracticeId();
        ReadingPractice readingPractice = baseMapper.selectById(practiceId);
        if (readingPractice == null) {
            return Result.fail("提交阅读练习题失败, ID不存在");
        }

        // 检查题目数量
        Integer exerciseCount = readingPractice.getExerciseCount();
        List<@Valid ExerciseDTO> exercises = readingPracticeDTO.getExercises().stream()
                .distinct()
                .collect(Collectors.toList());
        if (exercises.size() != exerciseCount) {
            return Result.fail("提交阅读练习题失败, 题目数量不匹配");
        }

        List<ExerciseVO> answerList = exerciseMapper.selectExerciseByReadingPracticeId(true, practiceId);
        // 正确答案数量计数
        AtomicInteger correctCount = new AtomicInteger();
        List<ExerciseRecord> exerciseRecords = exerciseService.checkExercises(answerList, exercises, RecordType.READING, correctCount);
        if (exerciseRecords.size() != exerciseCount) {
            return Result.fail("提交阅读练习题失败, 题目数量不匹配");
        }

        // 插入练习记录
        int userId = StpUtil.getLoginIdAsInt();
        ReadingRecord readingRecord = new ReadingRecord();
        readingRecord.setReadingPracticeId(practiceId);
        readingRecord.setUserId(userId);
        readingRecord.setScore(Math.round((float) correctCount.get() / exerciseCount * 100));
        readingRecordMapper.insert(readingRecord);

        Integer readingRecordId = readingRecord.getReadingRecordId();
        exerciseRecords.forEach(exerciseRecord -> {
            exerciseRecord.setRecordId(readingRecordId);
        });
        // 批量插入
        exerciseRecordService.saveBatch(exerciseRecords);
        return Result.success("提交阅读练习题成功", readingRecord);
    }

    @Override
    public Result getRecords(Page<ReadingRecordsVO> pageDto) {
        return null;
    }

    @Override
    public Result getRecord(Integer readingRecordId) {
        return null;
    }

    private ReadingPracticeVO getReadingPracticeVO(boolean needAnswer, Integer readingPracticeId) {
        ReadingPractice readingPractice = baseMapper.selectById(readingPracticeId);
        if (readingPractice == null) {
            return null;
        }
        ReadingPracticeVO readingPracticeVO = ReadingPracticeVO.from(readingPractice);
        List<ExerciseVO> exerciseVOS = exerciseMapper.selectExerciseByReadingPracticeId(needAnswer, readingPracticeId);
        readingPracticeVO.setExercises(exerciseVOS);
        return readingPracticeVO;
    }
}




