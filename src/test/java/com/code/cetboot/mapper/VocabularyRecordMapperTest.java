package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@SpringBootTest
public class VocabularyRecordMapperTest {

    @Resource
    private VocabularyRecordMapper vocabularyRecordMapper;

    @Resource
    private Logger logger;

    @Test
    public void selectByUserIdAndPageTest() {
        Page<VocabularyRecordsVO> page = new Page<>();
        page.setCurrent(3);
        page.setSize(2);
        Page<VocabularyRecordsVO> voPage = vocabularyRecordMapper.selectByUserIdAndPage(page, 1);
        logger.info("voPage: {}", voPage.getRecords());
    }
}
