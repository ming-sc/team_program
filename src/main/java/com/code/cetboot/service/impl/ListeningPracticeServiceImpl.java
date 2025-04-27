package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.RecordType;
import com.code.cetboot.dto.ExerciseDTO;
import com.code.cetboot.dto.ListeningPracticeDTO;
import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.entity.ListeningPractice;
import com.code.cetboot.entity.ListeningRecord;
import com.code.cetboot.exception.ServiceException;
import com.code.cetboot.mapper.ExerciseMapper;
import com.code.cetboot.mapper.ListeningRecordMapper;
import com.code.cetboot.service.ExerciseRecordService;
import com.code.cetboot.service.ListeningPracticeService;
import com.code.cetboot.mapper.ListeningPracticeMapper;
import com.code.cetboot.vo.ExerciseVO;
import com.code.cetboot.vo.listening.ListeningPracticeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
* @author 19735
* @description 针对表【listening_practice】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class ListeningPracticeServiceImpl extends ServiceImpl<ListeningPracticeMapper, ListeningPractice>
    implements ListeningPracticeService{

    @Resource
    private ExerciseMapper exerciseMapper;

    @Resource
    private ListeningRecordMapper recordMapper;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @Override
    public Result getPractices(Page<ListeningPractice> pageDto, String keyword) {
        Page<ListeningPractice> page = baseMapper.selectListeningPracticeByKeyword(pageDto, keyword);
        return Result.success("获取听力练习题成功", PageResult.of(page));
    }

    @Override
    public Result getPractice(Integer listeningPracticeId) {
        ListeningPractice listeningPractice = baseMapper.selectById(listeningPracticeId);
        if (listeningPractice == null) {
            return Result.fail("获取听力练习题失败，ID不存在");
        }
        ListeningPracticeVO listeningPracticeVO = ListeningPracticeVO.from(listeningPractice);
        List<ExerciseVO> exerciseVOS = exerciseMapper.selectExerciseByListeningPracticeId(false, listeningPracticeId);
        listeningPracticeVO.setExercises(exerciseVOS);
        return Result.success("获取听力练习题成功", listeningPracticeVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result submit(ListeningPracticeDTO listeningPracticeDTO) {
        Integer practiceId = listeningPracticeDTO.getListeningPracticeId();
        ListeningPractice listeningPractice = baseMapper.selectById(practiceId);
        if (listeningPractice == null) {
            return Result.fail("提交听力练习题失败，ID不存在");
        }

        // 检查题目数量
        Integer exerciseCount = listeningPractice.getExerciseCount();
        List<@Valid ExerciseDTO> exercises = listeningPracticeDTO.getExercises().stream()
                .distinct()
                .collect(Collectors.toList());
        if (exercises.size() != exerciseCount) {
            return Result.fail("提交听力练习题失败，题目数量不匹配");
        }

        List<ExerciseVO> answerList = exerciseMapper.selectExerciseByListeningPracticeId(true, practiceId);
        // 转为 HashMap
        Map<Integer, ExerciseVO> answerMap = answerList.stream()
                .collect(Collectors.toMap(ExerciseVO::getExerciseId, v -> v));
        int userId = StpUtil.getLoginIdAsInt();
        AtomicInteger correctCount = new AtomicInteger();
        List<ExerciseRecord> exerciseRecords = exercises.stream()
                .map(exerciseDTO -> {
                    Integer exerciseId = exerciseDTO.getExerciseId();
                    Integer selectionId = exerciseDTO.getSelectionId();
                    ExerciseVO exerciseVO = answerMap.get(exerciseId);
                    if (exerciseVO == null) {
                        return null; // 题目不存在
                    }

                    // 检查选项是否存在
                    exerciseVO.getSelections().stream()
                            .filter(selection -> selection.getExerciseSelectionId().equals(selectionId))
                            .findFirst()
                            .orElseThrow(() -> new ServiceException("提交听力练习题失败，选项不存在"));

                    // 创建练习记录
                    ExerciseRecord exerciseRecord = new ExerciseRecord();
                    exerciseRecord.setExerciseId(exerciseId);
                    exerciseRecord.setExerciseSelectionId(selectionId);
                    exerciseRecord.setUserId(userId);
                    if (exerciseVO.getAnswerSelectionId().equals(selectionId)) {
                        correctCount.getAndIncrement();
                        exerciseRecord.setIsCorrect(1);
                    } else {
                        exerciseRecord.setIsCorrect(0);
                    }
                    exerciseRecord.setRecordType(RecordType.LISTENING);
                    return exerciseRecord;
                }).filter(Objects::nonNull).collect(Collectors.toList());

        if (exerciseRecords.size() != exerciseCount) {
            return Result.fail("提交听力练习题失败，题目数量不匹配");
        }

        // 插入听力练习记录
        ListeningRecord listeningRecord = new ListeningRecord();
        listeningRecord.setListeningPracticeId(practiceId);
        listeningRecord.setUserId(userId);
        listeningRecord.setScore(Math.round((float) correctCount.get() / exerciseCount * 100));
        recordMapper.insert(listeningRecord);

        exerciseRecords.forEach(exerciseRecord -> {
            exerciseRecord.setRecordId(listeningRecord.getListeningRecordId());
        });
        // 批量插入
        exerciseRecordService.saveBatch(exerciseRecords);

        return Result.success("提交听力练习题成功", listeningRecord);
    }

    @Override
    public Result getRecords(Page<ListeningPracticeVO> pageDto) {
        return null;
    }

    @Override
    public Result getRecord(Integer listeningPracticeId) {
        return null;
    }
}




