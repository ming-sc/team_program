package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName exercise_record
 */
@TableName(value ="exercise_record")
@Data
public class ExerciseRecord implements Serializable {
    /**
     * 做题记录id
     */
    @TableId
    private Integer exerciseRecordId;

    /**
     * 做题用户id
     */
    private Integer userId;

    /**
     * 题目id
     */
    private Integer exerciseId;

    /**
     * 所选选项id
     */
    private Integer exerciseSelectionId;

    /**
     * 是否正确 0 => 错误 1 => 正确
     */
    private Integer isCorrect;

    /**
     * 记录时间
     */
    private Date recordTime;

    /**
     * 听力练习/阅读练习id 由记录类型字段决定类型
     */
    private Integer recordId;

    /**
     * 记录类型 0 => 听力练习 1 => 阅读练习
     */
    private Integer recordType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}