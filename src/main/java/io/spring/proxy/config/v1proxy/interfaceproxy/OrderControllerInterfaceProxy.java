package io.spring.proxy.config.v1proxy.interfaceproxy;

import io.spring.proxy.app.v1.OrderControllerV1;
import io.spring.proxy.trace.TraceStatus;
import io.spring.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 target; // 실제객체 참조
    private final LogTrace logTrace;
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
