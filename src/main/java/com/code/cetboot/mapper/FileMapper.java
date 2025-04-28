package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 19735
* @description 针对表【file】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.File
*/
@Mapper
public interface FileMapper extends BaseMapper<File> {

    /**
     * 分页查询文件
     *
     * @param pageDto 分页参数
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<File> selectFileByKeyword(Page<File> pageDto,@Param("keyword") String keyword);
}




