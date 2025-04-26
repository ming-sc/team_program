package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName exercise
 */
@TableName(value ="exercise")
@Data
public class Exercise implements Serializable {
    /**
     * 题目id
     */
    @TableId(type = IdType.AUTO)
    private Integer exerciseId;

    /**
     * 题干
     */
    private String content;

    /**
     * 正确答案选项id
     */
    private Integer answerSelectionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}