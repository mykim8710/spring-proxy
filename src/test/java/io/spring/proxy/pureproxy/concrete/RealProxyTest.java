package io.spring.proxy.pureproxy.concrete;

import io.spring.proxy.pureproxy.concrete.code.CacheProxy;
import io.spring.proxy.pureproxy.concrete.code.RealClient;
import io.spring.proxy.pureproxy.concrete.code.RealLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RealProxyTest {
    @Test
    @DisplayName("[캐싱] 구체클래스만 있는 곳에서 프록시가 적용이 안되어 있다.")
    void noCacheProxyTest() throws Exception {
        RealLogic realLogic = new RealLogic();
        RealClient realClient = new RealClient(realLogic);
        realClient.execute();
        realClient.execute();
        realClient.execute();
    }

    @Test
    @DisplayName("[캐싱] 구체클래스를 상속해서 정의한 프록시를 적용했다.")
    void cacheProxyTest() throws Exception {
        RealLogic realLogic = new RealLogic();
        CacheProxy cacheProxy = new CacheProxy(realLogic);
        RealClient realClient = new RealClient(cacheProxy);
        realClient.execute();
        realClient.execute();
        realClient.execute();
    }
}
