package io.spring.proxy.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BasicTest {
    @Test
    @DisplayName("스프링에서 일반적인 빈 등록과정이다.")
    void basicSpringBeanAddTest() throws Exception {
        // 스프링 컨테이너 생성
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        // A는 빈으로 등록
        A beanA = applicationContext.getBean("beanA", A.class);
        beanA.helloA();
        assertThat(beanA).isNotNull();

        // B는 빈으로 등록하지 않는다.
        assertThatThrownBy(() -> applicationContext.getBean(B.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }

    @Slf4j
    static class A{
        public void helloA() {
            log.info("Hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("Hello B");
        }
    }
}
