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
 * @TableName vocabulary_record
 */
@TableName(value ="vocabulary_record")
@Data
public class VocabularyRecord implements Serializable {
    /**
     * 词汇学习记录id
     */
    @TableId(type = IdType.AUTO)
    private Integer vocabularyRecordId;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 词汇id
     */
    private Integer vocabularyId;

    /**
     * 是否正确 0 => 错误 1 => 正确
     */
    private Integer isCorrect;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}