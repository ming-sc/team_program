package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.entity.ListeningPractice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 19735
* @description 针对表【listening_practice】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.ListeningPractice
*/
@Mapper
public interface ListeningPracticeMapper extends BaseMapper<ListeningPractice> {

    /**
     * 分页查询听力练习题
     *
     * @param page 分页参数
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<ListeningPractice> selectListeningPracticeByKeyword(Page<ListeningPractice> page, @Param("keyword") String keyword);
}




