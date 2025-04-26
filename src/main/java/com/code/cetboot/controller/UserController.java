package com.code.cetboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.UserDTO;
import com.code.cetboot.service.UserService;
import com.code.cetboot.validation.UserDTOValidation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param UserDTO 用户信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(
            @Validated(UserDTOValidation.LoginValidation.class) @RequestBody UserDTO UserDTO
    ) {
        return userService.login(UserDTO);
    }

    /**
     * 用户注册
     * @param userDTO 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(
            @Validated(UserDTOValidation.RegisterValidation.class) @RequestBody UserDTO userDTO
    ) {
        return userService.register(userDTO);
    }

    /**
     * 用户注销
     * @return 注销结果
     */
    @PostMapping("/logout")
    @SaCheckLogin
    public Result logout() {
        StpUtil.logout();
        return Result.success("注销成功");
    }
}
