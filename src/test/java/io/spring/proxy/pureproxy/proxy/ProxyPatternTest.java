package io.spring.proxy.pureproxy.proxy;

import io.spring.proxy.pureproxy.proxy.code.CacheProxy;
import io.spring.proxy.pureproxy.proxy.code.ProxyPatternClient;
import io.spring.proxy.pureproxy.proxy.code.RealSubject;
import io.spring.proxy.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    @Test
    @DisplayName("프록시 패턴이 적용이 안되었다.")
    void noProxyPatternTest() throws Exception {
        RealSubject realSubject = new RealSubject();

        ProxyPatternClient proxyPatternClient = new ProxyPatternClient(realSubject);
        proxyPatternClient.execute();   // 1초
        proxyPatternClient.execute();   // 1초
        proxyPatternClient.execute();   // 1초
    }

    @Test
    @DisplayName("프록시 패턴이 적용되었다.")
    void cacheProxyPatternTest() throws Exception {
        Subject subject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(subject);

        ProxyPatternClient proxyPatternClient = new ProxyPatternClient(cacheProxy);
        proxyPatternClient.execute();   // 처음만 실제 객체 호출
        proxyPatternClient.execute();   // 프록시에 캐시된 값 반화
        proxyPatternClient.execute();   // 프록시에 캐시된 값 반화
    }
}
