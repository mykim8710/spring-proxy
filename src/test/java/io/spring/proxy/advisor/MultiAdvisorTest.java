package io.spring.proxy.advisor;

import io.spring.proxy.common.service.ServiceImpl;
import io.spring.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class MultiAdvisorTest {

    @Test
    @DisplayName("여러개의 Advise를 적용하기 위해 각 프록시를 생성한다.")
    void makeMultiProxyTest() throws Exception {
        // client -> proxy2(advisor2) -> proxy1(advisor1) -> target

        // target 생성
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리1 생성
        ProxyFactory proxyFactory1 = new ProxyFactory(target);

        // advisor1 생성
        Advisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());

        // Advisor1를 프록시 팩토리1에 추가
        proxyFactory1.addAdvisor(advisor1);

        // 프록시1 생성
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();


        // 프록시 팩토리2 생성
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);

        // advisor2 생성
        Advisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        // Advisor2를 프록시 팩토리2에 추가
        proxyFactory2.addAdvisor(advisor2);

        // 프록시2 생성
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        proxy2.save();
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 호출");
            return invocation.proceed();
        }
    }
    @Slf4j
    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 호출");
            return invocation.proceed();
        }
    }

    @Test
    @DisplayName("여러개의 Advisor를 하나의 프록시 팩토리에 추가한 뒤 프록시를 생성한다.")
    void addMultiAdvisorToProxyFactoryTest() throws Exception {
        // target 생성
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // advisor1, advisor2 생성
        Advisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        Advisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        // advisor2, advisor1를 프록시 팩토리에 추가 -> 순서대로 적용된다.
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);

        // 프록시 생성
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
    }

}
