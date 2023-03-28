package io.spring.proxy.config.v1proxy.interfaceproxy;

import io.spring.proxy.app.v1.OrderRepositoryV1;
import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {
    private final OrderRepositoryV1 target; // 실제객체 참조
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.begin("OrderRepository.save()");

            // 로직(target) 호출
            target.save(itemId);

            logTrace.end(traceStatus);
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}
