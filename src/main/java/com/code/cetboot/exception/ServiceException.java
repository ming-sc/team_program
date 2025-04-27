package com.code.cetboot.exception;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String message;
    private final int code;

    public ServiceException(String message) {
        this(message, 500);
    }

    public ServiceException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
