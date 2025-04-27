package com.code.cetboot.dto;

import com.code.cetboot.validation.VocabularyPracticeValidation;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class VocabularyPracticeDTO {

    /**
     * 回答的中文意思
     */
    @NotNull(
            message = "选项不能为空",
            groups = {VocabularyPracticeValidation.Submit.class}
    )
    private String meaning;

    /**
     * 词汇id
     */
    @NotNull(
            message = "词汇id不能为空",
            groups = {VocabularyPracticeValidation.Submit.class}
    )
    private long vocabularyId;
}
