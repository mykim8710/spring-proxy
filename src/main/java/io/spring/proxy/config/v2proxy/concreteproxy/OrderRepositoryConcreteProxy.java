package io.spring.proxy.config.v2proxy.concreteproxy;

import io.spring.proxy.app.v2.OrderRepositoryV2;
import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {
    private final OrderRepositoryV2 target; // 실제객체 참조
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
