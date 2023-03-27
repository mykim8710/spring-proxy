package io.spring.proxy.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {
    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        // ab99e16f-3cde-4d24-8241-256108c203a2
        // 생성된 UUID : ab99e16f(앞 8자리만 사용)
        return UUID.randomUUID().toString().substring(0,8);
    }

    /**
     * createNextId()
     * 다음 TraceId 를 만든다.
     * 깊이가 증가해도 트랜잭션ID는 같다.
     * 대신에 깊이가 하나 증가한다.
     */
    public TraceId createNextId() {
        return new TraceId(id, level+1);
    }

    /**
     * createPreviousId()
     * createNextId()의 반대 역할을 한다.
     * id 는 기존과 같고, level은 하나 감소한다.
     */
    public TraceId createPreviousId() {
        return new TraceId(id, level-1);
    }

    /**
     * isFirstLevel()
     * 첫 번째 레벨 여부를 편리하게 확인할 수 있는 메서드
     */
    public boolean isFirstLevel() {
        return this.level == 0;
    }

}
