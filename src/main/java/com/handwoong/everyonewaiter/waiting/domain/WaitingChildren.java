package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingChildren(int value) {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
