package io.spring.proxy.pureproxy.concrete.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {
    private ConcreteLogic realConcreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.realConcreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = realConcreteLogic.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime; log.info("TimeDecorator 종료 resultTime={}ms", resultTime);
        return result;
    }
}
