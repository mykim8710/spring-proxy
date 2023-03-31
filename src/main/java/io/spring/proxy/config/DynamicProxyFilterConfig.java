package io.spring.proxy.config;

import io.spring.proxy.app.v1.*;
import io.spring.proxy.config.v1proxy.dynamicproxy.LogTraceBasicHandler;
import io.spring.proxy.config.v1proxy.dynamicproxy.LogTraceFilterHandler;
import io.spring.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {
    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        // target
        OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();

        // 프록시 생성
        OrderRepositoryV1 proxy = (OrderRepositoryV1)Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceFilterHandler(orderRepositoryV1, logTrace, PATTERNS));

        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        // target
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));

        // 프록시 생성
        OrderServiceV1 proxy = (OrderServiceV1)Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceFilterHandler(orderServiceV1, logTrace, PATTERNS));

        return proxy;

    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        // target
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));

        // 프록시 생성
        OrderControllerV1 proxy = (OrderControllerV1)Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceFilterHandler(orderControllerV1, logTrace, PATTERNS));

        return proxy;
    }

}
