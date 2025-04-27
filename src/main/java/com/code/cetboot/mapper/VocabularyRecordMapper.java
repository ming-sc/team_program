package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.entity.VocabularyRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 19735
* @description 针对表【vocabulary_record】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.VocabularyRecord
*/
@Mapper
public interface VocabularyRecordMapper extends BaseMapper<VocabularyRecord> {

    Page<VocabularyRecordsVO> selectByUserIdAndPage(Page<VocabularyRecordsVO> pageDTO, @Param("userId") Integer userId);
}




