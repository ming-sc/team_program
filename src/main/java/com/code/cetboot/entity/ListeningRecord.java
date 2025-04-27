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
 * @TableName listening_record
 */
@TableName(value ="listening_record")
@Data
public class ListeningRecord implements Serializable {
    /**
     * 听力记录id
     */
    @TableId(type = IdType.AUTO)
    private Integer listeningRecordId;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 听力练习id
     */
    private Integer listeningPracticeId;

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