package com.code.cetboot.controller;

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

    @PostMapping("/login")
    public Result login(
            @Validated(UserDTOValidation.LoginValidation.class) @RequestBody UserDTO UserDTO
    ) {
        return userService.login(UserDTO);
    }

    @PostMapping("/register")
    public Result register(
            @Validated(UserDTOValidation.RegisterValidation.class) @RequestBody UserDTO userDTO
    ) {
        return userService.register(userDTO);
    }
}
