package com.code.cetboot.dto;

import com.code.cetboot.validation.ReadingPracticeValidation;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ReadingPracticeDTO {

    @NotEmpty(message = "提交数据不能为空", groups = {ReadingPracticeValidation.Submit.class})
    @NotNull(message = "提交数据不能为空", groups = {ReadingPracticeValidation.Submit.class})
    private List<@Valid ExerciseDTO> exercises;

    /**
     * 阅读练习id
     */
    @NotNull(message = "阅读练习id不能为空", groups = {ReadingPracticeValidation.Submit.class})
    private Integer readingPracticeId;
}
