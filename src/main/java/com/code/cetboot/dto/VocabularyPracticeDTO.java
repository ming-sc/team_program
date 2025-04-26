package com.code.cetboot.dto;

import lombok.Data;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class VocabularyPracticeDTO {

    /**
     * 回答的中文意思
     */
    private String meaning;

    /**
     * 词汇id
     */
    private long vocabularyId;
}
