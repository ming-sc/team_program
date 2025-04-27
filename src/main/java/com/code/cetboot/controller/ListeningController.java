package com.code.cetboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.ListeningPracticeDTO;
import com.code.cetboot.entity.ListeningPractice;
import com.code.cetboot.service.ListeningPracticeService;
import com.code.cetboot.validation.ListeningPracticeValidation;
import com.code.cetboot.vo.listening.ListeningPracticeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@RestController
@RequestMapping("/listening")
public class ListeningController {

    @Resource
    private ListeningPracticeService listeningService;

    @GetMapping("/getPractices")
    public Result getPractices(
            Page<ListeningPractice> pageDto,
            @RequestParam String keyword
    ) {
        return listeningService.getPractices(pageDto, keyword);
    }

    @GetMapping("/getPractice")
    public Result getPractice(
            @RequestParam Integer listeningPracticeId
    ) {
        return listeningService.getPractice(listeningPracticeId);
    }

    @PostMapping("/submit")
    public Result submit(
            @Validated(ListeningPracticeValidation.Submit.class)
            @RequestBody ListeningPracticeDTO listeningPracticeDTO
    ) {
        return listeningService.submit(listeningPracticeDTO);
    }

    @GetMapping("/getRecords")
    public Result getRecords(
            Page<ListeningPracticeVO> pageDto
    ) {
        return listeningService.getRecords(pageDto);
    }

    @GetMapping("/getRecord")
    public Result getRecord(
            @RequestParam Integer listeningPracticeId
    ) {
        return listeningService.getRecord(listeningPracticeId);
    }
}
