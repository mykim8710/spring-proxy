package io.spring.proxy.pureproxy.proxy.code;

import io.spring.proxy.trace.threadlocal.code.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealSubject implements Subject {
    @Override
    public String operation() {
        log.info("실제 객체 호출");

        ThreadUtil.sleep(1000);

        return "data";
    }
}
