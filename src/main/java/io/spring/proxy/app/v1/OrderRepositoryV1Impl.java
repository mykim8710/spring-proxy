package io.spring.proxy.app.v1;

import io.spring.proxy.global.ThreadUtil;

public class OrderRepositoryV1Impl implements OrderRepositoryV1 {
    @Override
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }

        ThreadUtil.sleep(1000);
    }
}
