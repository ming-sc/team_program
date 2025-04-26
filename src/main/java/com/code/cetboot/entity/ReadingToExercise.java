package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName reading_to_exercise
 */
@TableName(value ="reading_to_exercise")
@Data
public class ReadingToExercise implements Serializable {
    /**
     * 中间表id
     */
    @TableId(type = IdType.AUTO)
    private Integer mapperId;

    /**
     * 阅读理解id
     */
    private Integer readingPracticeId;

    /**
     * 对应的题目id
     */
    private Integer exerciseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}