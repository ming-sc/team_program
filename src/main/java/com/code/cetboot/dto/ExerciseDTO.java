package com.code.cetboot.dto;

import lombok.Data;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ExerciseDTO {
    /**
     * 选择题id
     */
    private long exerciseId;

    /**
     * 所选选项id
     */
    private long selectionId;
}
