package com.code.cetboot.vo.listening;

import com.code.cetboot.vo.ExerciseVO;
import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ListeningPracticeVO {

    /**
     * 听力音频地址
     */
    private String audio;

    /**
     * 题目数量
     */
    private Long exerciseCount;

    private List<ExerciseVO> exercises;

    /**
     * 听力练习id
     */
    private long listeningPracticeId;

    /**
     * 听力练习标题
     */
    private String title;
}
