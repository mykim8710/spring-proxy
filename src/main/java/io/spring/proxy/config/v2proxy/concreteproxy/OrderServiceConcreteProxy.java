package io.spring.proxy.config.v2proxy.concreteproxy;

import io.spring.proxy.app.v2.OrderServiceV2;
import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target; // 실제객체 참조
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

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
