package com.ivm.CustomerDetect.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class APIAccessingLog {
    @Pointcut("execution(public * com.ivm.CustomerDetect.controller.*.*(..))")
    public void webLog(){}

    @Pointcut("execution(public * com.ivm.CustomerDetect.ControllerExceptionHandler.*(..))")
    public void exception(){}

    private static final Logger logger = LoggerFactory.getLogger(APIAccessingLog.class);

    @Before("webLog(){}")
    public void printURLInfo(JoinPoint joinPoint)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();  

        logger.info(
            "URL: {}, HTTP Method: {} FROM: {}; Depatched by {} with {}", 
            request.getRequestURL().toString(),
            request.getMethod(),
            request.getRemoteAddr(),
            joinPoint.getSignature().getDeclaringTypeName(),
            Arrays.toString(joinPoint.getArgs())
        );
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()") 
    public void printResultBriefing(Object ret) throws Throwable
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();  

        logger.info(
            "URL: {}, HTTP Method: {} FROM: {}; Returns: \n{}", 
            request.getRequestURL().toString(),
            request.getMethod(),
            request.getRemoteAddr(),
            ret == null ? "void" : ret.toString().replace('\n', ' ')
        );
    }

    @Before("exception(){}")
    public void printException(JoinPoint joinPoint)
    {
        Object [] exs = joinPoint.getArgs();
        if(exs == null || exs.length == 0 || !(exs[0] instanceof Exception))
            return;
        Exception ex = (Exception)exs[0];
        logger.error("Exception: {}:{}", ex.getLocalizedMessage(), ex.getMessage());
        StringBuilder builder = new StringBuilder();
        for(StackTraceElement trace : ex.getStackTrace())
        {
            builder.append(trace.toString());
            builder.append('\n');
        }
        logger.error("{}", builder.toString());
    }
}