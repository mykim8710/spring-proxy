package io.spring.proxy.config.v2proxy;

import io.spring.proxy.app.v2.OrderControllerV2;
import io.spring.proxy.app.v2.OrderRepositoryV2;
import io.spring.proxy.app.v2.OrderServiceV2;
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
public class ProxyFactoryConfigV2 {
    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        // target
        OrderRepositoryV2 target = new OrderRepositoryV2();

        // Proxy Factory
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor : Pointcut + Advice
        Advisor advisor = getAdvisor(logTrace);
        proxyFactory.addAdvisor(advisor);

        // proxy
        OrderRepositoryV2 proxy = (OrderRepositoryV2)proxyFactory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        // target
        OrderServiceV2 target = new OrderServiceV2(orderRepositoryV2(logTrace));

        // Proxy Factory
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor : Pointcut + Advice
        Advisor advisor = getAdvisor(logTrace);
        proxyFactory.addAdvisor(advisor);

        // proxy
        OrderServiceV2 proxy = (OrderServiceV2)proxyFactory.getProxy();
        log.info("ProxyFactory proxy={}, target={}", proxy.getClass());
        return proxy;
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        // target
        OrderControllerV2 target = new OrderControllerV2(orderServiceV2(logTrace));

        // Proxy Factory
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor : Pointcut + Advice
        Advisor advisor = getAdvisor(logTrace);
        proxyFactory.addAdvisor(advisor);

        // proxy
        OrderControllerV2 proxy = (OrderControllerV2)proxyFactory.getProxy();
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
