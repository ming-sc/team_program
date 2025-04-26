package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName listening_practice
 */
@TableName(value ="listening_practice")
@Data
public class ListeningPractice implements Serializable {
    /**
     * 听力练习id
     */
    @TableId(type = IdType.AUTO)
    private Integer listeningPracticeId;

    /**
     * 听力练习标题
     */
    private String title;

    /**
     * 听力音频地址
     */
    private String audio;

    /**
     * 题目数量
     */
    private Integer exerciseCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}