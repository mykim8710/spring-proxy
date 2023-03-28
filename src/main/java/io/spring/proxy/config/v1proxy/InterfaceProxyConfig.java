package io.spring.proxy.config.v1proxy;

import io.spring.proxy.app.v1.*;
import io.spring.proxy.config.v1proxy.interfaceproxy.OrderControllerInterfaceProxy;
import io.spring.proxy.config.v1proxy.interfaceproxy.OrderRepositoryInterfaceProxy;
import io.spring.proxy.config.v1proxy.interfaceproxy.OrderServiceInterfaceProxy;
import io.spring.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl orderControllerV1Impl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(orderControllerV1Impl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceV1Impl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceV1Impl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);
    }
}
