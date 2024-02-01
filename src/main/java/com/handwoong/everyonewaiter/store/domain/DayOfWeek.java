package com.handwoong.everyonewaiter.store.domain;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DayOfWeek {
    MONDAY {
        @Override
        public String toString() {
            return "월";
        }
    },
    TUESDAY {
        @Override
        public String toString() {
            return "화";
        }
    },
    WEDNESDAY {
        @Override
        public String toString() {
            return "수";
        }
    },
    THURSDAY {
        @Override
        public String toString() {
            return "목";
        }
    },
    FRIDAY {
        @Override
        public String toString() {
            return "금";
        }
    },
    SATURDAY {
        @Override
        public String toString() {
            return "토";
        }
    },
    SUNDAY {
        @Override
        public String toString() {
            return "일";
        }
    },
    ;

    public static DayOfWeek from(final String value) {
        return Arrays.stream(DayOfWeek.values())
            .filter(dayOfWeek -> dayOfWeek.toString().equals(value))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(value + "(은)는 유효하지 않은 요일입니다."));
    }
}
