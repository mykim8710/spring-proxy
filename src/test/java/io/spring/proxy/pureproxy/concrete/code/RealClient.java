package io.spring.proxy.pureproxy.concrete.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealClient {
    private RealLogic realLogic;

    public RealClient(RealLogic realLogic) {
        this.realLogic = realLogic;
    }

    public void execute() {
        realLogic.operation();
    }
}
