package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName vocabulary
 */
@TableName(value ="vocabulary")
@Data
public class Vocabulary implements Serializable {
    /**
     * 词汇id
     */
    @TableId(type = IdType.AUTO)
    private Integer vocabularyId;

    /**
     * 词汇
     */
    private String word;

    /**
     * 中文意思
     */
    private String meaning;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}