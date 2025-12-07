package com.happy.exception;

import com.happy.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error("出错啦，请联系程序员~");
    }

    @ExceptionHandler
    public Result handleRuntimeException(RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        return Result.error("对不起,出错了,不能直接删除!");
    }
}
