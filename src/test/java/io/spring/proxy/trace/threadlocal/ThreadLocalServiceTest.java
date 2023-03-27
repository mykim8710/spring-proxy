package io.spring.proxy.trace.threadlocal;

import io.spring.proxy.trace.threadlocal.code.ThreadLocalService;
import io.spring.proxy.trace.threadlocal.code.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ThreadLocalServiceTest {
    private ThreadLocalService threadLocalService = new ThreadLocalService();

    @Test
    void threadLocalServiceTest() {
        log.info("main start");

        Runnable userA = () -> threadLocalService.logic("userA");
        Runnable userB = () -> threadLocalService.logic("userB");

        Thread threadA = new Thread(userA); // thread 생성
        threadA.setName("Thread-A");     // thread name 설정

        Thread threadB = new Thread(userB);
        threadB.setName("Thread-B");

        threadA.start();
        ThreadUtil.sleep(100);
        threadB.start();

        ThreadUtil.sleep(3000); // 메인 쓰레드 종료 대기(Main Thread 종료로 인해 Thread-B가 바로 끝나버리는 것을 방지)
        log.info("main exit");
    }
}