package com.sky.aop;

import com.sky.anno.Annotation;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class aop {
    @Around("@annotation(com.sky.anno.Annotation)")
    public Object localTime(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args.length == 0) {
            return pjp.proceed();
        }
        Object arg = args[0];

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Annotation annotation = signature.getMethod().getAnnotation(Annotation.class);
        OperationType operationType = annotation.value();
        if (operationType == OperationType.INSERT) {
            arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(arg, LocalDateTime.now());
            arg.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class).invoke(arg, LocalDateTime.now());
            arg.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(arg, BaseContext.getCurrentId());
            arg.getClass().getDeclaredMethod("setCreateUser", Long.class).invoke(arg, BaseContext.getCurrentId());
        }
        if (operationType == OperationType.UPDATE) {
            arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(arg, LocalDateTime.now());
            arg.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(arg, BaseContext.getCurrentId());
        }
        Object result = pjp.proceed();
        return result;
    }
}