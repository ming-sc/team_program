package com.code.cetboot.service;

import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.UserDTO;
import com.code.cetboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 19735
* @description 针对表【user】的数据库操作Service
* @createDate 2025-04-26 01:53:46
*/
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param userDTO 用户信息
     * @return 登录结果
     */
    Result login(UserDTO userDTO);

    /**
     * 用户注册
     * @param userDTO 用户信息
     * @return 注册结果
     */
    Result register(UserDTO userDTO);
}
