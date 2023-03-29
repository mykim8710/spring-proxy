package io.spring.proxy.cglib;

import io.spring.proxy.cglib.code.TimeMethodInterceptor;
import io.spring.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {
    @Test
    @DisplayName("인터페이스가 없는 구체 클래스에서 cglib를 사용해서 동적프록시를 적용한다.")
    void noInterfaceClassCglibTest() throws Exception {
        // Object target(구체 클래스)
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));

        // 프록시 생성
        ConcreteService proxy = (ConcreteService)enhancer.create();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();
    }
}
