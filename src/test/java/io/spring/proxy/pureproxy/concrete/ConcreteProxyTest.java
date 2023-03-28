package io.spring.proxy.pureproxy.concrete;

import io.spring.proxy.pureproxy.concrete.code.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {
    @Test
    @DisplayName("[부가기능_시간측정] 구체클래스만 있는 곳에서 프록시가 적용이 안되어 있다.")
    void noTimeProxyTest() throws Exception {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient concreteClient = new ConcreteClient(concreteLogic);
        concreteClient.execute();
    }

    @Test
    @DisplayName("[부가기능_시간측정] 구체클래스를 상속해서 정의한 프록시를 적용했다.")
    void timeProxyTest() throws Exception {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient concreteClient = new ConcreteClient(timeProxy);
        concreteClient.execute();
    }
}
