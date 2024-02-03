package com.handwoong.everyonewaiter.store.domain;

public record StoreOptionId(Long value) {

    @Override
    public String toString() {
        return value.toString();
    }
}
