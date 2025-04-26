package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName exercise_selection
 */
@TableName(value ="exercise_selection")
@Data
public class ExerciseSelection implements Serializable {
    /**
     * 选项id
     */
    @TableId(type = IdType.AUTO)
    private Integer exerciseSelectionId;

    /**
     * 选项内容
     */
    private String selection;

    /**
     * 对应题目id
     */
    private Integer exerciseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}