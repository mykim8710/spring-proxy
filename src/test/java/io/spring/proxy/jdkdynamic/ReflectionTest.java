package io.spring.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {
    @Test
    @DisplayName("자바 리플렉션 기술을 사용하지 않았다.")
    void noRefectionTest() throws Exception {
        Hello target = new Hello();

        // 공통 로직1 실행
        log.info("start");
        String resultA = target.callA();
        log.info("resultA = {}", resultA);
        // 공통 로직1 종료

        // 공통 로직2 실행
        log.info("start");
        String resultB = target.callB();
        log.info("resultB = {}", resultB);
        // 공통 로직2 종료
    }

    @Test
    @DisplayName("자바 리플렉션 기술을 사용해서 공통로직을 실행한다.1")
    void javaRefection1Test() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 메타정보를 획득(내부 클래스는 구분을 위해 $ 를 사용)
        Class clazzHello = Class.forName("io.spring.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보
        log.info("start");
        Method methodCallA = clazzHello.getMethod("callA"); // 해당 클래스의 call 메서드 메타정보를 획득
        Object resultA = methodCallA.invoke(target);  // 획득한 메서드 메타정보로 실제 인스턴스의 메서드를 호출
        log.info("resultA = {}", resultA);

        // callB 메서드 정보
        log.info("start");
        Method methodCallB = clazzHello.getMethod("callB");
        Object resultB = methodCallB.invoke(target);
        log.info("resultB = {}", resultB);
    }

    @Test
    @DisplayName("자바 리플렉션 기술을 사용해서 공통로직을 메서드로 뽑아낸 뒤 실행한다.")
    void javaRefection2Test() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 메타정보를 획득(내부 클래스는 구분을 위해 $ 를 사용)
        Class clazzHello = Class.forName("io.spring.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = clazzHello.getMethod("callA"); // 해당 클래스의 call 메서드 메타정보를 획득
        dynamicMethodCall(methodCallA, target);

        Method methodCallB = clazzHello.getMethod("callB"); // 해당 클래스의 call 메서드 메타정보를 획득
        dynamicMethodCall(methodCallB, target);
    }

    private void dynamicMethodCall(Method method, Object target) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callA");
            return "B";
        }

    }
}
