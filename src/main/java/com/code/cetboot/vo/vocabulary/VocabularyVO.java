package com.code.cetboot.vo.vocabulary;

import lombok.Data;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class VocabularyVO {

    /**
     * 词汇id
     */
    private long vocabularyId;

    /**
     * 词汇选项
     */
    private List<String> selections;

    /**
     * 词汇
     */
    private String word;
}
