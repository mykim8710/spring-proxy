package io.spring.proxy.config.v1proxy.dynamicproxy;

import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {
    private final Object target;    // 호출할 대상
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        TraceStatus traceStatus = null;
        try {
            // 메서드 명 얻어오기
            // pattern : save, request, reque*, *est.....
            String methodName = method.getName();
            // 호출된 메서드명이 정의해 놓은 패턴과 맞는지 확인 -> 맞는다면 동적 프록시 적용
            if(!PatternMatchUtils.simpleMatch(patterns, methodName)) {
                // 매칭이 안되면 target 바로 호출
                return method.invoke(target, args);
            }

            // OrderController.request() message정보 - method로부터 가져온다.
            // method.getDeclaringClass().getSimpleName() : 메서드의 메타정보에서 클래스명을 가져온다.
            // method.getName(): 메서드 명
            // OrderController.request()
            String message = method.getDeclaringClass().getSimpleName() + "." + methodName +"()";
            traceStatus = logTrace.begin(message);

            // 로직(target) 호출
            Object result = method.invoke(target, args);

            logTrace.end(traceStatus);
            return result;
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}
