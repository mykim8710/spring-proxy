package io.spring.proxy.config.aspect;

import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }


    @Around("execution(* io.spring.proxy.app..*(..)) && !execution(* io.spring.proxy.app..noLog(..))")  // pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        //  advice(부가기능 로직)
        TraceStatus traceStatus = null;
        try {
            String message = joinPoint.getSignature().toShortString();

            traceStatus = logTrace.begin(message);

            // 로직(target) 호출
            Object result = joinPoint.proceed();

            logTrace.end(traceStatus);

            return result;
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}