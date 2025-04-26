package com.code.cetboot.vo.reading;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ReadingRecordsVO {

    /**
     * 阅读练习id
     */
    private long readingPracticeId;

    /**
     * 阅读练习记录id
     */
    private long readingRecordId;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 得分
     */
    private Long score;

    /**
     * 所属用户id
     */
    private Long userId;

    /**
     * 题目数量
     */
    private Long exerciseCount;

    /**
     * 阅读理解标题
     */
    private String title;
}
