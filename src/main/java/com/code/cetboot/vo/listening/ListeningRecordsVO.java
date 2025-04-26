package com.code.cetboot.vo.listening;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class ListeningRecordsVO {
    /**
     * 听力练习id
     */
    private long listeningPracticeId;

    /**
     * 听力记录id
     */
    private long listeningRecordId;

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
     * 听力练习标题
     */
    private String title;
}
