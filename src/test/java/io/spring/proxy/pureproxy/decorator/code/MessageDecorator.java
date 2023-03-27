package io.spring.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

/**
* 응답값을 꾸며주는 데코레이터
**/
@Slf4j
public class MessageDecorator implements Component{
    private Component target;

    public MessageDecorator(Component target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        // data -> *** data ***
        String result = target.operation(); // 리얼객체 호출, return data
        String decoratedResult = "***  " + result + " ***";

        log.info("MessageDecorator 꾸미기 적용 전 = {}, 적용 후 = {}", result, decoratedResult);
        return decoratedResult;
    }
}
