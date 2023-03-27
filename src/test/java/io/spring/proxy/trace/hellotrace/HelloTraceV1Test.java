package io.spring.proxy.trace.hellotrace;

import io.spring.proxy.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {

    @Test
    @DisplayName("HelloTraceV1의 begin() -> end() 테스트")
    void beginEndTest() throws Exception {
        HelloTraceV1 helloTrace = new HelloTraceV1();
        TraceStatus traceStatus = helloTrace.begin("hello");
        helloTrace.end(traceStatus);
    }

    @Test
    @DisplayName("HelloTraceV1의 begin() -> exception() 테스트")
    void beginExceptionTest() throws Exception {
        HelloTraceV1 helloTrace = new HelloTraceV1();
        TraceStatus traceStatus = helloTrace.begin("hello");
        helloTrace.exception(traceStatus, new IllegalStateException());
    }

}