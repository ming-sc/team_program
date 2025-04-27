package com.code.cetboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.ListeningPracticeDTO;
import com.code.cetboot.entity.ListeningPractice;
import com.code.cetboot.mapper.ExerciseMapper;
import com.code.cetboot.service.ListeningPracticeService;
import com.code.cetboot.mapper.ListeningPracticeMapper;
import com.code.cetboot.vo.ExerciseVO;
import com.code.cetboot.vo.listening.ListeningPracticeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public Result submit(ListeningPracticeDTO listeningPracticeDTO) {
        return null;
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




