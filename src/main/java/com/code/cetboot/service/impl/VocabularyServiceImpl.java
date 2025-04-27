package com.code.cetboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.VocabularyPracticeDTO;
import com.code.cetboot.entity.Vocabulary;
import com.code.cetboot.service.VocabularyService;
import com.code.cetboot.mapper.VocabularyMapper;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;
import com.code.cetboot.vo.vocabulary.VocabularyVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
* @author 19735
* @description 针对表【vocabulary】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class VocabularyServiceImpl extends ServiceImpl<VocabularyMapper, Vocabulary>
    implements VocabularyService{

    @Override
    public Result getPractices(Integer count) {
        if (count <= 0) {
            return Result.fail("获取词汇练习失败");
        }
        List<Vocabulary> vocabularies = baseMapper.selectBatchRandom(count * 4);
        int needCount = count;
        if (vocabularies.size() < count * 4) {
            needCount = vocabularies.size() / 4;
        }
        if (needCount == 0) {
            return Result.fail("获取词汇练习失败");
        }
        List<VocabularyVO> vocabularyVOList = IntStream.range(0, needCount)
                .mapToObj(i -> {
                    Vocabulary vocabulary = vocabularies.get(0);
                    vocabularies.remove(0);
                    VocabularyVO vocabularyVO = new VocabularyVO();
                    vocabularyVO.setVocabularyId(vocabulary.getVocabularyId());
                    vocabularyVO.setWord(vocabulary.getWord());
                    // 生成选项
                    List<String> selections = IntStream.range(0, 3)
                            .mapToObj(j -> vocabularies.get(j).getMeaning())
                            .collect(Collectors.toList());
                    selections.add(vocabulary.getMeaning());
                    // 打乱选项顺序
                    Collections.shuffle(selections);

                    vocabularyVO.setSelections(selections);
                    return vocabularyVO;
                }).collect(Collectors.toList());
        return Result.success("获取词汇练习成功", vocabularyVOList);
    }

    @Override
    public Result submit(List<VocabularyPracticeDTO> vocabularyPracticeList) {
        return null;
    }

    @Override
    public Result getRecords(Page<VocabularyRecordsVO> pageDto) {
        return null;
    }
}




