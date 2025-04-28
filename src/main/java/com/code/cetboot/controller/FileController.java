package com.code.cetboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.Role;
import com.code.cetboot.entity.File;
import com.code.cetboot.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/28
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @SaCheckLogin
    @SaCheckRole({Role.ADMIN_STRING})
    public Result upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName
    ) throws Exception {
        return fileService.uploadFile(file, fileName);
    }

    @GetMapping("/search")
    @SaCheckLogin
    public Result search(
            Page<File> pageDto,
            @RequestParam String keyword
    ) {
        return fileService.searchFile(pageDto, keyword);
    }
}
