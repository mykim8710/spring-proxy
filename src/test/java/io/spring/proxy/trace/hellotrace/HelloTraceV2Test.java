package io.spring.proxy.trace.hellotrace;

import io.spring.proxy.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {
    @Test
    @DisplayName("HelloTraceV2의 begin() -> end(), beginSync() -> end() 테스트")
    void beginEndBeginSyncEndTest() throws Exception {
        HelloTraceV2 helloTrace = new HelloTraceV2();
        TraceStatus traceStatus1= helloTrace.begin("hello");
        TraceStatus traceStatus2 = helloTrace.beginSync(traceStatus1.getTraceId(), "hello2");
        helloTrace.end(traceStatus2);
        helloTrace.end(traceStatus1);
    }

    @Test
    @DisplayName("HelloTraceV2의 begin() -> exception(), beginSync() -> exception() 테스트")
    void beginExceptionBeginSyncExceptionTest() throws Exception {
        HelloTraceV2 helloTrace = new HelloTraceV2();
        TraceStatus traceStatus1 = helloTrace.begin("hello");
        TraceStatus traceStatus2 = helloTrace.beginSync(traceStatus1.getTraceId(), "hello2");
        helloTrace.exception(traceStatus2, new IllegalStateException());
        helloTrace.exception(traceStatus1, new IllegalStateException());
    }

}