package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingChildren(int value) {

    public static final int MIN_CHILDREN = 0;
    public static final int MAX_CHILDREN = 20;
    public static final String MIN_CHILDREN_MESSAGE = "어린이의 최소 인원은 0이상으로 입력해 주세요.";
    public static final String MAX_CHILDREN_MESSAGE = "어린이의 최대 인원은 20명 이하이어야 합니다.";

    public WaitingChildren {
        validate(value);
    }

    private void validate(final int value) {
        if (value < MIN_CHILDREN) {
            throw new IllegalArgumentException(MIN_CHILDREN_MESSAGE);
        }
        if (value > MAX_CHILDREN) {
            throw new IllegalArgumentException(MAX_CHILDREN_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
