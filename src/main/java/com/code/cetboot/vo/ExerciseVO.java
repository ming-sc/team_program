package com.code.cetboot.vo;

import com.code.cetboot.entity.ExerciseSelection;
import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ExerciseVO {

    /**
     * 正确答案选项id, 除获取练习记录外都不返回此字段!
     */
    private Long answerSelectionId;

    /**
     * 题干
     */
    private String content;

    /**
     * 题目id
     */
    private long exerciseId;
    private List<ExerciseSelection> selections;
}
