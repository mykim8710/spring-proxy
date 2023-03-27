package io.spring.proxy.app.v2;

import io.spring.proxy.global.ThreadUtil;

public class OrderRepositoryV2  {
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }

        ThreadUtil.sleep(1000);
    }
}
