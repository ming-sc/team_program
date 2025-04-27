package com.code.cetboot.dto;

import com.code.cetboot.validation.ListeningPracticeValidation;
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
public class ListeningPracticeDTO {

    @NotEmpty(message = "提交数据不能为空", groups = {ListeningPracticeValidation.Submit.class})
    @NotNull(message = "提交数据不能为空", groups = {ListeningPracticeValidation.Submit.class})
    private List<@Valid ExerciseDTO> exercises;

    /**
     * 听力练习id
     */
    @NotNull(message = "听力练习id不能为空", groups = {ListeningPracticeValidation.Submit.class})
    private Integer listeningPracticeId;
}
