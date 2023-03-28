package io.spring.proxy.pureproxy.concrete.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy extends RealLogic {
    private RealLogic realLogic;
    private String cacheValue;

    public CacheProxy(RealLogic realLogic) {
        this.realLogic = realLogic;
    }

    @Override
    public String operation() {
        log.info("CacheProxy 실행");

        if(cacheValue == null) {
            cacheValue = realLogic.operation();
        }
        return cacheValue;
    }
}
