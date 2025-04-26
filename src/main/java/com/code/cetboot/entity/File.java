package com.code.cetboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName file
 */
@TableName(value ="file")
@Data
public class File implements Serializable {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Integer fileId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件地址
     */
    private String src;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}