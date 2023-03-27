package io.spring.proxy.trace.templatecallback;

import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceTemplate {
    private final LogTrace logTrace;

    public <T> T execute(String message, TraceCallback<T> traceCallback) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.begin(message);

            // 로직 호출
            T result = traceCallback.call();

            logTrace.end(traceStatus);
            return result;
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}
