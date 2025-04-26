package com.code.cetboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户角色
     */
    private Integer role;
}
