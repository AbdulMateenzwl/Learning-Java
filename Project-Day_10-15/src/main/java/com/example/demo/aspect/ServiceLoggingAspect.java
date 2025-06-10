package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Around("execution(* com.example.demo.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String correlationId = MDC.get("correlationId");

        log.info("Entering service method: {}.{} [correlationId: {}]", 
            className, 
            methodName, 
            correlationId);

        long startTime = System.currentTimeMillis();
        Object result;
        
        try {
            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("Completed service method: {}.{} [duration: {}ms, correlationId: {}]",
                className,
                methodName,
                (endTime - startTime),
                correlationId);
            return result;
        } catch (Exception e) {
            log.error("Error in service method: {}.{} [error: {}, correlationId: {}]",
                className,
                methodName,
                e.getMessage(),
                correlationId);
            throw e;
        }
    }
}
