package io.spring.proxy.advisor;

import io.spring.proxy.common.advice.TimeAdvice;
import io.spring.proxy.common.service.ServiceImpl;
import io.spring.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    @DisplayName("Advisor 생성하고 프록시 팩토리에 추가한 뒤 프록시를 생성한다.")
    void addAdvisorToProxyFactoryTest() throws Exception {
        // target 생성
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // advisor 생성
        // Pointcut.TRUE : 항상 참인 Pointcut
        // new TimeAdvice() : Advice
        PointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        // Advisor를 프록시 팩토리에 추가
        proxyFactory.addAdvisor(advisor);

        // 프록시 생성
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }


    @Test
    @DisplayName("직접 구현한 Pointcut을 적용한 Advisor 생성하고 프록시 팩토리에 추가한 뒤 프록시를 생성한다.")
    void addAdvisorWithPointcutToProxyFactoryTest() throws Exception {
        // target 생성
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // advisor 생성
        // Pointcut.TRUE : 항상 참인 Pointcut
        // new TimeAdvice() : Advice
        PointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

        // Advisor를 프록시 팩토리에 추가
        proxyFactory.addAdvisor(advisor);

        // 프록시 생성
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;    // ClassFilter는 항상 TRUE
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    @Slf4j
    static class MyMethodMatcher implements MethodMatcher{
        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            // 메서드 명이 save인 경우에만 advise적용
            boolean result = method.getName().equals(matchName);
            log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
            log.info("포인트컷 결과 result={}", result);
            return result;
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    @DisplayName("스프링이 제공하는 Pointcut을 적용한 Advisor 생성하고 프록시 팩토리에 추가한 뒤 프록시를 생성한다.")
    void addAdvisorWithSpringPointcutToProxyFactoryTest() throws Exception {
        // target 생성
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // advisor 생성
        // Spring에서 제공하는 Pointcut : NameMatchMethodPointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save"); // method명이 save인 경우에만 Advise적용
        PointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

        // Advisor를 프록시 팩토리에 추가
        proxyFactory.addAdvisor(advisor);

        // 프록시 생성
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}
