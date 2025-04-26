package com.code.cetboot.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ListeningPracticeDTO {

    private List<ExerciseDTO> exercises;

    /**
     * 听力练习id
     */
    private long listeningPracticeId;
}
