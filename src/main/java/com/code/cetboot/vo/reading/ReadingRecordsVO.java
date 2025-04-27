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
    private Integer readingPracticeId;

    /**
     * 阅读练习记录id
     */
    private Integer readingRecordId;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 题目数量
     */
    private Integer exerciseCount;

    /**
     * 阅读理解标题
     */
    private String title;
}
