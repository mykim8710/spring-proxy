package io.spring.proxy.config.v2proxy;

import io.spring.proxy.app.v2.OrderControllerV2;
import io.spring.proxy.app.v2.OrderRepositoryV2;
import io.spring.proxy.app.v2.OrderServiceV2;
import io.spring.proxy.config.v2proxy.concreteproxy.OrderControllerConcreteProxy;
import io.spring.proxy.config.v2proxy.concreteproxy.OrderRepositoryConcreteProxy;
import io.spring.proxy.config.v2proxy.concreteproxy.OrderServiceConcreteProxy;
import io.spring.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {
    @Bean
    public OrderControllerConcreteProxy orderControllerConcreteProxy(LogTrace logTrace) {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderServiceConcreteProxy(logTrace));
        return new OrderControllerConcreteProxy(orderControllerV2, logTrace);
    }

    @Bean
    public OrderServiceConcreteProxy orderServiceConcreteProxy(LogTrace logTrace) {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepositoryConcreteProxy(logTrace));
        return new OrderServiceConcreteProxy(orderServiceV2, logTrace);
    }

    @Bean
    public OrderRepositoryConcreteProxy orderRepositoryConcreteProxy(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(orderRepositoryV2, logTrace);
    }

}
