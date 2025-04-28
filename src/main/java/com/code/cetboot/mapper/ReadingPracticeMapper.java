package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.entity.ReadingPractice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 19735
* @description 针对表【reading_practice】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.ReadingPractice
*/
@Mapper
public interface ReadingPracticeMapper extends BaseMapper<ReadingPractice> {

    /**
     * 分页查询阅读练习题
     *
     * @param page 分页参数
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<ReadingPractice> selectReadingPracticeByKeyword(Page<ReadingPractice> page,@Param("keyword") String keyword);
}




