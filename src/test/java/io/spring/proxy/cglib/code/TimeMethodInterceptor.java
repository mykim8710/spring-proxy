package io.spring.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {
    private final Object target;    // 호출대상 클래스

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("TimeProxy 실행");

        long startTime = System.currentTimeMillis();

        // Object result = method.invoke(target, args);
        Object result = proxy.invoke(target, args); // 위보다 성능상 이점이 있다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime; log.info("TimeProxy 종료 resultTime={}", resultTime); return result;
    }
}
