package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingAdult(int value) {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
