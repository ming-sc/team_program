package com.code.cetboot.vo.reading;

import com.code.cetboot.vo.ExerciseVO;
import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ReadingPracticeVO {

    /**
     * 阅读理解文章
     */
    private String content;

    /**
     * 题目数量
     */
    private Integer exerciseCount;

    private List<ExerciseVO> exercises;

    /**
     * 阅读理解id
     */
    private Integer readingPracticeId;

    /**
     * 阅读理解标题
     */
    private String title;
}
