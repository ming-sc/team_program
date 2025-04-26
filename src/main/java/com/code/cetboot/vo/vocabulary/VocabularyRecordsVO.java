package com.code.cetboot.vo.vocabulary;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class VocabularyRecordsVO {

    /**
     * 中文意思
     */
    private String meaning;

    /**
     * 词汇id
     */
    private long vocabularyId;

    /**
     * 词汇
     */
    private String word;

    /**
     * 是否正确 0 => 错误 1 => 正确
     */
    private Long isCorrect;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 所属用户id
     */
    private Long userId;

    /**
     * 词汇学习记录id
     */
    private long vocabularyRecordId;
}
