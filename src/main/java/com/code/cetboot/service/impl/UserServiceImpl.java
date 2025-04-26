package com.code.cetboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.entity.User;
import com.code.cetboot.service.UserService;
import com.code.cetboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 19735
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




