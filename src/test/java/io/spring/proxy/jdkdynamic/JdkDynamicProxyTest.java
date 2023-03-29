package io.spring.proxy.jdkdynamic;

import io.spring.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {
    @Test
    @DisplayName("AImpl에 동적 프록시를 적용한다.")
    void dynamicProxyAImplTest() throws Exception {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);  // 동적 프록시에 적용할 로직

        // 프록시 생성
        // AInterface.class.getClassLoader() : 어디에 생성될지 지정, classLoader
        // new Class[]{AInterface.class} : 어떤 인터페이스 기반으로 프록시를 만들지 해당 인터페이스
        // handler : 프록시가 호출할 로직
        AInterface proxy = (AInterface)Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        proxy.call();   // 프록시에서 call() 호출, handler의 로직 실행

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }

    @Test
    @DisplayName("BImpl에 동적 프록시를 적용한다.")
    void dynamicProxyBImplTest() throws Exception {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);  // 동적 프록시에 적용할 로직

        // 프록시 생성
        // AInterface.class.getClassLoader() : 어디에 생성될지 지정, classLoader
        // new Class[]{AInterface.class} : 어떤 인터페이스 기반으로 프록시를 만들지 해당 인터페이스
        // handler : 프록시가 호출할 로직
        BInterface proxy = (BInterface)Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();   // 프록시에서 call() 호출, handler의 로직 실행

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }
}
