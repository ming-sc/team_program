package com.code.cetboot.dto;

import com.code.cetboot.validation.ListeningPracticeValidation;
import com.code.cetboot.validation.ReadingPracticeValidation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
@EqualsAndHashCode(of = {"exerciseId"})
public class ExerciseDTO {
    /**
     * 选择题id
     */
    @NotNull(message = "选择题id不能为空",
            groups = {
                ListeningPracticeValidation.Submit.class,
                ReadingPracticeValidation.Submit.class
            }
    )
    private Integer exerciseId;

    /**
     * 所选选项id
     */
    @NotNull(message = "所选选项id不能为空",
            groups = {
                ListeningPracticeValidation.Submit.class,
                ReadingPracticeValidation.Submit.class
            }
    )
    private Integer selectionId;
}
