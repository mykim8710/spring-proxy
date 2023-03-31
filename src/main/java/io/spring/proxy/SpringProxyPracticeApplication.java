package io.spring.proxy;

import io.spring.proxy.config.AopConfig;
import io.spring.proxy.config.AutoProxyConfig;
import io.spring.proxy.config.BeanPostProcessorConfig;
import io.spring.proxy.config.ProxyFactoryConfigV2;
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
//@Import({DynamicProxyFilterConfig.class})
//@Import({ProxyFactoryConfigV1.class})
//@Import({ProxyFactoryConfigV2.class})
//@Import({BeanPostProcessorConfig.class})
//@Import({AutoProxyConfig.class})
@Import({AopConfig.class})
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