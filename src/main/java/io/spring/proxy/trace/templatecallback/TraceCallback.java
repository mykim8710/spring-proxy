package io.spring.proxy.trace.templatecallback;

public interface TraceCallback<T> {
    T call();
}
