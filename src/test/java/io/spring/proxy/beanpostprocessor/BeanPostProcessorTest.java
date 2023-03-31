package io.spring.proxy.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BeanPostProcessorTest {
    @Test
    @DisplayName("구현한 빈후처리기를 이용해서 A객체를 B객체로 바꾼다음 스프링 컨테이너에 등록한다.")
    void aToBByBeanPostProcessorTest() throws Exception {
        // 스프링 컨테이너 생성
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        // beanA의 이름이로 B객체가 스프링 컨테이너에 등록된다.
        B beanB = applicationContext.getBean("beanA", B.class);
        beanB.helloB();
        assertThat(beanB).isNotNull();

        assertThatThrownBy(() -> applicationContext.getBean(A.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBBeanPostProcessor aToBBeanPostProcessor() {
            return new AToBBeanPostProcessor();
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

    @Slf4j
    static class AToBBeanPostProcessor implements BeanPostProcessor {
        // 객체 생성 이후에 @PostConstruct 같은 초기화가 발생하기 전에 호출되는 포스트 프로세서
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {}, bean = {}", beanName, bean);

            // 등록하려는 빈의 타입이 A라면 B로 리턴
            if(bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }
}
