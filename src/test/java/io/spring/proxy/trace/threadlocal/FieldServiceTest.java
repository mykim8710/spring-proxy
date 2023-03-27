package io.spring.proxy.trace.threadlocal;

import io.spring.proxy.trace.threadlocal.code.FieldService;
import io.spring.proxy.trace.threadlocal.code.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class FieldServiceTest {
    private FieldService fieldService = new FieldService();

    @Test
    void fieldServiceTest() {
        log.info("main start");

        Runnable userA = () -> fieldService.logic("userA");
        Runnable userB = () -> fieldService.logic("userB");

        Thread threadA = new Thread(userA); // thread 생성
        threadA.setName("Thread-A");     // thread name 설정

        Thread threadB = new Thread(userB);
        threadB.setName("Thread-B");

        threadA.start();
        //ThreadUtil.sleep(2000);  // 동시성 문제 발생 X : Thread-A 의 실행이 끝나고 나서 Thread-B 가 실행
        ThreadUtil.sleep(100);  // 동시성 문제 발생 O : Thread-A의 실행이 끝나기전에 Thread-B 가 실행
        threadB.start();

        ThreadUtil.sleep(3000); // 메인 쓰레드 종료 대기(Main Thread 종료로 인해 Thread-B가 바로 끝나버리는 것을 방지)
        log.info("main exit");
    }
}