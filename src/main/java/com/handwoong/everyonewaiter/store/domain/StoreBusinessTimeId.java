package com.handwoong.everyonewaiter.store.domain;

public record StoreBusinessTimeId(Long value) {

    @Override
    public String toString() {
        return value.toString();
    }
}
