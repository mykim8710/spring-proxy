package io.spring.proxy.pureproxy.decorator;

import io.spring.proxy.pureproxy.decorator.code.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {
    @Test
    @DisplayName("데코레이터 패턴이 적용이 안되어있다.")
    void noDecoratorPatterTest() throws Exception {
        Component realComponent = new RealComponent();
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(realComponent);
        decoratorPatternClient.execute();
    }

    @Test
    @DisplayName("데코레이터 패턴이 적용이 되어있다. : 글자 꾸미기")
    void decoratorPatterTest1() throws Exception {
        Component realComponent = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(messageDecorator);
        decoratorPatternClient.execute();
    }

    @Test
    @DisplayName("데코레이터 패턴이 적용이 되어있다. : 실행시간 시간측정 + 글자 꾸미기")
    void decoratorPatterTest2() throws Exception {
        Component realComponent = new RealComponent();
        MessageDecorator messageDecorator = new MessageDecorator(realComponent);
        TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);  // 프록시 체인
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(timeDecorator);
        decoratorPatternClient.execute();
    }
}
