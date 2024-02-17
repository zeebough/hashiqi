package com.ghtt.socialplatform.controller;

import com.ghtt.socialplatform.controller.exceptions.BusinessException;
import com.ghtt.socialplatform.controller.exceptions.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ExceptionProcessor {
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException businessException){
        log.warn(Arrays.toString(businessException.getStackTrace()),businessException);
        return new Result(businessException.getCode(),businessException.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException systemException){
        //记录日志
        log.error(Arrays.toString(systemException.getStackTrace()),systemException);
        //发送运维
        return new Result(systemException.getCode(),systemException.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public Result doNumberFormatException(NumberFormatException numberFormatException){
        //记录日志
        log.warn(Arrays.toString(numberFormatException.getStackTrace()),numberFormatException);
        //发送运维
        return new Result(Code.ILLEGAL_INPUT,"请输入正确的数字");
    }

    @ExceptionHandler(ClassCastException.class)
    public Result doClassCastException(ClassCastException classCastException){
        //记录日志
        log.error(Arrays.toString(classCastException.getStackTrace()),classCastException);
        //发送运维
        return new Result(Code.TRANSFORM_ERR,"数据类型转换有误");
    }

    @ExceptionHandler(IOException.class)
    public Result doIOException(IOException ioException){
        //记录日志
        log.error(Arrays.toString(ioException.getStackTrace()),ioException);
        //发送运维
        return new Result(Code.IO_ERR,"数据读写有误，请稍后再试");
    }

    @ExceptionHandler(ReflectiveOperationException.class)
    public Result doReflectiveOperationException(ReflectiveOperationException reflectiveOperationException){
        //记录日志
        log.error(Arrays.toString(reflectiveOperationException.getStackTrace()),reflectiveOperationException);
        //发送运维
        //发送开发
        return new Result(Code.UNKNOWN_ERR,"数据获取或绑定有误，请您稍后再试");
    }

    @ExceptionHandler(AssertionError.class)
    public Result doAssertionError(AssertionError assertionError){
        log.error(Arrays.toString(assertionError.getStackTrace()),assertionError);
        return new Result(Code.ILLEGAL_INPUT,"请检查您的输入");
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Result doSQLSyntaxErrorException(SQLSyntaxErrorException sqlSyntaxErrorException){
        log.error(Arrays.toString(sqlSyntaxErrorException.getStackTrace()),sqlSyntaxErrorException);
        return new Result(Code.ILLEGAL_INPUT,"请检查您的输入，不要输入不存在的东西！");
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception){
        //记录日志
        log.error(Arrays.toString(exception.getStackTrace()),exception);
        //发送运维
        //发送开发
        return new Result(Code.UNKNOWN_ERR,"系统繁忙，请您稍后再试");
    }
}
