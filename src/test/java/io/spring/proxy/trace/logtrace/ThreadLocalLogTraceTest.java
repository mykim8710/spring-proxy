package io.spring.proxy.trace.logtrace;

import io.spring.proxy.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThreadLocalLogTraceTest {
    ThreadLocalLogTrace threadLocalLogTrace = new ThreadLocalLogTrace();

    @Test
    @DisplayName("ThreadLocalLogTrace의 begin() -> end(), Level2 테스트")
    void beginEndLevel2Test() throws Exception {
        TraceStatus traceStatus1 = threadLocalLogTrace.begin("hello");
        TraceStatus traceStatus2 = threadLocalLogTrace.begin("hello2");
        TraceStatus traceStatus3 = threadLocalLogTrace.begin("hello3");
        threadLocalLogTrace.end(traceStatus3);
        threadLocalLogTrace.end(traceStatus2);
        threadLocalLogTrace.end(traceStatus1);
    }

    @Test
    @DisplayName("ThreadLocalLogTrace의 begin() -> exception(), Level2 테스트")
    void beginExceptionLevel2Test() throws Exception {
        TraceStatus traceStatus1 = threadLocalLogTrace.begin("hello");
        TraceStatus traceStatus2 = threadLocalLogTrace.begin("hello2");
        TraceStatus traceStatus3 = threadLocalLogTrace.begin("hello3");
        threadLocalLogTrace.exception(traceStatus3, new IllegalArgumentException());
        threadLocalLogTrace.exception(traceStatus2, new IllegalArgumentException());
        threadLocalLogTrace.exception(traceStatus1, new IllegalArgumentException());
    }
}