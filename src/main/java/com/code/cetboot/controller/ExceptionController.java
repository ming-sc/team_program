package com.code.cetboot.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.code.cetboot.bean.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return Result.fail("请求参数不完整或有误");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.fail("请求方式不正确");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Result.fail("请求参数类型不正确");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        return Result.fail("服务器异常，请稍后再试!");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getMessage().contains("Duplicate entry")) {
            return Result.fail("数据库中存在重复的唯一值，操作失败。");
        } else {
            return Result.fail("添加数据时发生未知错误");
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result paramExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return Result.fail(fieldError.getDefaultMessage());
            }
        }
        return Result.fail("请求参数不完整或有误");
    }

    @ExceptionHandler({NotLoginException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result notLoginExceptionExceptionHandler(NotLoginException e) {
        return Result.notLogin(e.getMessage());
    }

    @ExceptionHandler({NotRoleException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notRoleExceptionExceptionHandler(NotRoleException e) {
        return Result.notPermitted("您没有权限进行此操作");
    }

    @ExceptionHandler({NotPermissionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result notPermissionExceptionHandler(NotPermissionException e) {
        return Result.notPermitted("您没有权限进行此操作");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return Result.fail("参数转换错误");
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result bindExceptionHandler(BindException e) {
        return Result.fail("请求参数不完整或有误");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return Result.fail("请求参数不完整或有误");
    }
}
