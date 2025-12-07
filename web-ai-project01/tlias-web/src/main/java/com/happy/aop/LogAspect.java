package com.happy.aop;

import com.happy.Interceptor.LoginInterceptor;
import com.happy.mapper.EmpMapper;
import com.happy.mapper.LogMapper;
import com.happy.pojo.Log;
import com.happy.utils.CurrentHolder;
import com.happy.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.happy.anno.Annotation)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        long cost = end - start;

        Log l = new Log();
        l.setOperateEmpId(getCurrentUserId());
        l.setClassName(pjp.getSignature().getClass().getName());
        l.setOperateTime(LocalDateTime.now());
        l.setMethodName(pjp.getSignature().getName());
        l.setMethodParams(pjp.getArgs().toString());
        l.setReturnValue(proceed != null ? proceed.toString() : "void");
        l.setCostTime(cost);
        log.info("{}", l);
        logMapper.insert(l);
        return proceed;
    }

    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}
