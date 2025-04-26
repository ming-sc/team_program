package com.code.cetboot.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ReadingPracticeDTO {

    private List<ExerciseDTO> exercises;

    /**
     * 阅读练习id
     */
    private long readingPracticeId;
}
