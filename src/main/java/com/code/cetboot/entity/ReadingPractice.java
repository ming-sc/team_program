package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName reading_practice
 */
@TableName(value ="reading_practice")
@Data
public class ReadingPractice implements Serializable {
    /**
     * 阅读理解id
     */
    @TableId(type = IdType.AUTO)
    private Integer readingPracticeId;

    /**
     * 阅读理解标题
     */
    private String title;

    /**
     * 阅读理解文章
     */
    private String content;

    /**
     * 题目数量
     */
    private Integer exerciseCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}