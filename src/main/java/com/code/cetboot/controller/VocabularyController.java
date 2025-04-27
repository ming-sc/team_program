package com.code.cetboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.VocabularyPracticeDTO;
import com.code.cetboot.service.VocabularyService;
import com.code.cetboot.validation.VocabularyPracticeValidation;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@RestController
@RequestMapping("/vocabulary")
public class VocabularyController {

    @Resource
    private VocabularyService vocabularyService;

    @GetMapping("/getPractices")
    @SaCheckLogin
    public Result getPractices(
            @RequestParam Integer count
    ) {
        return vocabularyService.getPractices(count);
    }

    @PostMapping("submit")
    public Result submit(
            @Validated(VocabularyPracticeValidation.Submit.class)
            @RequestBody List<VocabularyPracticeDTO> vocabularyPracticeList
    ) {
        return vocabularyService.submit(vocabularyPracticeList);
    }

    @GetMapping("/getRecords")
    public Result getRecords(
            Page<VocabularyRecordsVO> pageDto
    ) {
        return vocabularyService.getRecords(pageDto);
    }
}
