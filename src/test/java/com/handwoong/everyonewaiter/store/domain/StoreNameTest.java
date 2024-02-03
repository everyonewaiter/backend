package com.handwoong.everyonewaiter.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.exception.InvalidStoreNameFormatException;
import org.junit.jupiter.api.Test;

class StoreNameTest {

    @Test
    void Should_Create_When_ValidStoreName() {
        // given
        final String storeName = "나루";

        // when
        final StoreName result = new StoreName(storeName);

        // then
        assertThat(result).hasToString(storeName);
    }

    @Test
    void Should_ThrowException_When_Null() {
        // expect
        assertThatThrownBy(() -> new StoreName(null))
            .isInstanceOf(InvalidStoreNameFormatException.class)
            .hasMessage("매장 이름을 입력해주세요.");
    }

    @Test
    void Should_ThrowException_When_Blank() {
        // expect
        assertThatThrownBy(() -> new StoreName("  "))
            .isInstanceOf(InvalidStoreNameFormatException.class)
            .hasMessage("매장 이름을 입력해주세요.");
    }

    @Test
    void Should_ThrowException_When_LengthGreaterThan50() {
        // given
        final String storeName = "나루 레스토랑".repeat(10);

        // expect
        assertThatThrownBy(() -> new StoreName(storeName))
            .isInstanceOf(InvalidStoreNameFormatException.class)
            .hasMessage("매장 이름은 50자 이하로 입력해주세요.");
    }
}
