package com.code.cetboot.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.code.cetboot.bean.UserData;
import com.code.cetboot.constant.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        UserData userData = (UserData) StpUtil.getSession().get("userData");
        Integer role = userData.getRole();
        ArrayList<String> list = new ArrayList<>();
        if (role.equals(Role.USER)) {
            list.add(Role.USER_STRING);
        } else if (role.equals(Role.ADMIN)) {
            list.add(Role.ADMIN_STRING);
        }
        return list;
    }
}
