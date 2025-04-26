package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName listening_to_exercise
 */
@TableName(value ="listening_to_exercise")
@Data
public class ListeningToExercise implements Serializable {
    /**
     * 中间表id
     */
    @TableId(type = IdType.AUTO)
    private Integer mapperId;

    /**
     * 听力练习id
     */
    private Integer listeningPracticeId;

    /**
     * 题目id
     */
    private Integer exerciseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}