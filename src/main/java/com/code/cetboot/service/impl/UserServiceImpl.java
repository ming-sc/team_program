package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.Result;
import com.code.cetboot.bean.UserData;
import com.code.cetboot.constant.SecretConst;
import com.code.cetboot.dto.UserDTO;
import com.code.cetboot.entity.User;
import com.code.cetboot.service.UserService;
import com.code.cetboot.mapper.UserMapper;
import com.code.cetboot.util.MD5Util;
import com.code.cetboot.vo.SignInInfoVO;
import org.springframework.stereotype.Service;

/**
* @author 19735
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public Result login(UserDTO userDTO) {
        if (StpUtil.isLogin()) {
            return Result.fail("请先注销当前登录");
        }
        String username = userDTO.getUserName();
        String password = userDTO.getPassword();
        password = MD5Util.encodeByMd5WithSalt(password, SecretConst.SALT);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserName, username)
                .eq(User::getPassword, password);

        User user = baseMapper.selectOne(wrapper);
        if (user == null) {
            return Result.fail("用户名或密码错误");
        }

        // 登录成功，设置用户信息到Session
        StpUtil.login(user.getUserId());
        StpUtil.getSession().set("userData", new UserData(user.getUserId(), user.getUserName(), user.getRole()));

        // 设置返回的登录信息
        SignInInfoVO signInInfoVO = new SignInInfoVO();
        signInInfoVO.setUserId(user.getUserId());
        signInInfoVO.setUserName(user.getUserName());
        signInInfoVO.setRole(user.getRole());
        signInInfoVO.setToken(StpUtil.getTokenValue());
        return Result.success("登录成功", signInInfoVO);
    }

    @Override
    public Result register(UserDTO userDTO) {
        String username = userDTO.getUserName();
        String password = userDTO.getPassword();

        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserName, username);
        User existingUser = baseMapper.selectOne(wrapper);
        if (existingUser != null) {
            return Result.fail("用户名已存在");
        }

        // 注册新用户
        password = MD5Util.encodeByMd5WithSalt(password, SecretConst.SALT);
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setRole(0);

        int result = baseMapper.insert(newUser);
        return result > 0 ? Result.success("注册成功") : Result.fail("注册失败");
    }
}




