package com.handwoong.everyonewaiter.waiting.domain;

public record WaitingTurn(int value) {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
