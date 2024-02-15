package com.handwoong.everyonewaiter.category.domain;

public record CategoryId(Long value) {

    @Override
    public String toString() {
        return value.toString();
    }
}
