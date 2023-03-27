package io.spring.proxy.trace.strategy;

import io.spring.proxy.trace.strategy.code.strategy.ContextV2;
import io.spring.proxy.trace.strategy.code.strategy.Strategy;
import io.spring.proxy.trace.strategy.code.strategy.StrategyLogic1;
import io.spring.proxy.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {
    @Test
    void strategyPatternTestV1() throws Exception {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    void strategyPatternTestV2() throws Exception {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        });

        context.execute(() -> log.info("비지니스 로직2 실행"));
    }
}
