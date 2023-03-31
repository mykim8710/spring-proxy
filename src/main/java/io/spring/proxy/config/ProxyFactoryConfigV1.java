package io.spring.proxy.config;

import io.spring.proxy.app.v1.*;
import io.spring.proxy.config.advice.LogTraceAdvice;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        // target
        OrderRepositoryV1 target = new OrderRepositoryV1Impl();

        // Proxy Factory
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor : Pointcut + Advice
        Advisor advisor = getAdvisor(logTrace);
        proxyFactory.addAdvisor(advisor);

        // proxy
        OrderRepositoryV1 proxy = (OrderRepositoryV1)proxyFactory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        // target
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

        // Proxy Factory
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor : Pointcut + Advice
        Advisor advisor = getAdvisor(logTrace);
        proxyFactory.addAdvisor(advisor);

        // proxy
        OrderServiceV1 proxy = (OrderServiceV1)proxyFactory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass());
        return proxy;
    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        // target
        OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));

        // Proxy Factory
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor : Pointcut + Advice
        Advisor advisor = getAdvisor(logTrace);
        proxyFactory.addAdvisor(advisor);

        // proxy
        OrderControllerV1 proxy = (OrderControllerV1)proxyFactory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass());
        return proxy;
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        // advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
