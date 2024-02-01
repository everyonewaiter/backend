package com.handwoong.everyonewaiter.store.domain;

public record StoreBreakTimeId(Long value) {

    @Override
    public String toString() {
        return value.toString();
    }
}
