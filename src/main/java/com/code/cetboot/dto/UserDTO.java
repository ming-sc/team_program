package com.code.cetboot.dto;

import com.code.cetboot.validation.UserDTOValidation;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Data
public class UserDTO {

    /**
     * 用户名
     */
    @NotNull(
            message = "用户名不能为空",
            groups = {
                    UserDTOValidation.LoginValidation.class,
                    UserDTOValidation.RegisterValidation.class
            }
    )
    private String userName;

    /**
     * 密码
     */
    @NotNull(
            message = "密码不能为空",
            groups = {
                    UserDTOValidation.LoginValidation.class,
                    UserDTOValidation.RegisterValidation.class
            }
    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\S]{6,18}$",
            message = "密码必须包含至少一个大写字母、一个小写字母和一个数字，长度为6到18个字符",
            groups = {UserDTOValidation.RegisterValidation.class}
    )
    private String password;
}
