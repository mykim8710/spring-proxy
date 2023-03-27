package io.spring.proxy.trace.strategy;

import io.spring.proxy.trace.strategy.code.templatecallback.Callback;
import io.spring.proxy.trace.strategy.code.templatecallback.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    @Test
    void templateCallbackPatternTest() throws Exception {
        TimeLogTemplate timeLogTemplate = new TimeLogTemplate();

        // 익명내부 클래스 사용
        timeLogTemplate.execute(new Callback() {
            @Override
            public void call() {
                log.info("비지니스 로직1");
            }
        });

        // 람다식 사용
        timeLogTemplate.execute(() ->log.info("비지니스 로직2"));
    }

}
