package io.spring.proxy.config.v2proxy.concreteproxy;

import io.spring.proxy.app.v2.OrderControllerV2;
import io.spring.proxy.app.v2.OrderServiceV2;
import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target; // 실제객체 참조
    private final LogTrace logTrace;

    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.begin("OrderController.request()");

            // 로직(target) 호출
            String result = target.request(itemId);

            logTrace.end(traceStatus);

            return result;
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        //  로그를 안찍는다.
        return target.noLog();
    }
}
