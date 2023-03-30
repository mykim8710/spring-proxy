package io.spring.proxy.proxyfactory;

import io.spring.proxy.common.advice.TimeAdvice;
import io.spring.proxy.common.service.ConcreteService;
import io.spring.proxy.common.service.ServiceImpl;
import io.spring.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("프록시팩토리, Advice를 사용해서 인터페이스가 있다면 JDK동적 프록시를 사용한다.")
    void adviceInterfaceProxyTest() throws Exception {
        // target(Interface)
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성 : target을 파라미터로 전달
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());   // advice(프록시에 적용하는 부가 기능 로직)

        // 프록시 생성
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        // ProxyFactory를 통해 만들었을때만 가능
        // 프록시 객체인가??
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();

        // JDK 동적 프록시인가?
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();

        // CGLIB 프록시인가?
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("프록시팩토리, Advice를 사용해서 구체클래스만 있다면 CGLIB 프록시를 사용한다.")
    void adviceConcreteClassProxyTest() throws Exception {
        // target(concrete class)
        ConcreteService target = new ConcreteService();

        // 프록시 팩토리 생성 : target을 파라미터로 전달
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());   // advice(프록시에 적용하는 부가 기능 로직)

        // 프록시 생성
        ConcreteService proxy = (ConcreteService)proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();

        // ProxyFactory를 통해 만들었을때만 가능
        // 프록시 객체인가??
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();

        // JDK 동적 프록시인가?
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();

        // CGLIB 프록시인가?
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }


    @Test
    @DisplayName("프록시팩토리, Advice를 사용해서 ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용한다.")
    void adviceInterfaceProxyTargetClassProxyTest() throws Exception {
        // target(Interface)
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성 : target을 파라미터로 전달
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //중요
        proxyFactory.addAdvice(new TimeAdvice());   // advice(프록시에 적용하는 부가 기능 로직)

        // 프록시 생성
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        // ProxyFactory를 통해 만들었을때만 가능
        // 프록시 객체인가??
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();

        // JDK 동적 프록시인가?
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();

        // CGLIB 프록시인가?
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
