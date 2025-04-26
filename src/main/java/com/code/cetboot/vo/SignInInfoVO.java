package com.code.cetboot.vo;

import lombok.Data;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class SignInInfoVO {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户角色
     */
    private Integer role;

    /**
     * token
     */
    private String token;
}
