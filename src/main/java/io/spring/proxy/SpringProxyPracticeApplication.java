package io.spring.proxy;

import io.spring.proxy.config.AppV1Config;
import io.spring.proxy.config.AppV2Config;
import io.spring.proxy.config.v1proxy.DynamicProxyBasicConfig;
import io.spring.proxy.config.v1proxy.DynamicProxyFilterConfig;
import io.spring.proxy.config.v1proxy.InterfaceProxyConfig;
import io.spring.proxy.config.v2proxy.ConcreteProxyConfig;
import io.spring.proxy.trace.logtrace.LogTrace;
import io.spring.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class, AppV2Config.class})
//@Import({InterfaceProxyConfig.class})
//@Import({ConcreteProxyConfig.class})
//@Import({DynamicProxyBasicConfig.class})
@Import({DynamicProxyFilterConfig.class})
@SpringBootApplication(scanBasePackages = "io.spring.proxy.app")
public class SpringProxyPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProxyPracticeApplication.class, args);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
