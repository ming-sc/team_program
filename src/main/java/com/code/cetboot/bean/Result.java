package com.code.cetboot.bean;

import com.code.cetboot.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Result {
    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private Object data;

    public static Result success() {
        return Result.builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .build();
    }

    public static Result success(String message) {
        return Result.builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .build();
    }

    public static Result success(String message, Object data) {
        return Result.builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static Result notLogin(String message) {
        return Result.builder()
                .code(ResultCode.NOT_LOGIN.getCode())
                .message(message)
                .build();
    }

    public static Result forbidden(String message) {
        return Result.builder()
                .code(ResultCode.FORBIDDEN.getCode())
                .message(message)
                .build();
    }
}
