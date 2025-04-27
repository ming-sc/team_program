package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName reading_record
 */
@TableName(value ="reading_record")
@Data
public class ReadingRecord implements Serializable {
    /**
     * 阅读练习记录id
     */
    @TableId(type = IdType.AUTO)
    private Integer readingRecordId;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 阅读练习id
     */
    private Integer readingPracticeId;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}