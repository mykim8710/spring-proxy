package io.spring.proxy.trace.logtrace;

import io.spring.proxy.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {
    FieldLogTrace fieldLogTrace = new FieldLogTrace();

    @Test
    @DisplayName("FieldLogTrace의 begin() -> end(), Level2 테스트")
    void beginEndLevel2Test() throws Exception {
        TraceStatus traceStatus1 = fieldLogTrace.begin("hello");
        TraceStatus traceStatus2 = fieldLogTrace.begin("hello2");
        TraceStatus traceStatus3 = fieldLogTrace.begin("hello3");
        fieldLogTrace.end(traceStatus3);
        fieldLogTrace.end(traceStatus2);
        fieldLogTrace.end(traceStatus1);
    }

    @Test
    @DisplayName("FieldLogTrace의 begin() -> exception(), Level2 테스트")
    void beginExceptionLevel2Test() throws Exception {
        TraceStatus traceStatus1 = fieldLogTrace.begin("hello");
        TraceStatus traceStatus2 = fieldLogTrace.begin("hello2");
        TraceStatus traceStatus3 = fieldLogTrace.begin("hello3");
        fieldLogTrace.exception(traceStatus3, new IllegalArgumentException());
        fieldLogTrace.exception(traceStatus2, new IllegalArgumentException());
        fieldLogTrace.exception(traceStatus1, new IllegalArgumentException());
    }
}