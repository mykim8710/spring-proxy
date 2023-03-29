package io.spring.proxy.config.v1proxy.dynamicproxy;

import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceBasicHandler implements InvocationHandler {
    private final Object target;    // 호출할 대상
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        TraceStatus traceStatus = null;
        try {
            // OrderController.request() message정보 - method로부터 가져온다.
            // method.getDeclaringClass().getSimpleName() : 메서드의 메타정보에서 클래스명을 가져온다.
            // method.getName(): 메서드 명
            // OrderController.request()
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() +"()";
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
