package io.spring.proxy.app.v3;

import io.spring.proxy.global.utils.ThreadUtil;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV3 {
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }

        ThreadUtil.sleep(1000);
    }
}
