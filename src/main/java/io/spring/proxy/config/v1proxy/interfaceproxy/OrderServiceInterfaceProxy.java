package io.spring.proxy.config.v1proxy.interfaceproxy;

import io.spring.proxy.app.v1.OrderServiceV1;
import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1 {

    private final OrderServiceV1 target; // 실제객체 참조
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.begin("OrderService.orderItem()");

            // 로직(target) 호출
            target.orderItem(itemId);

            logTrace.end(traceStatus);
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}
