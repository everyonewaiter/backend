package com.handwoong.everyonewaiter.waiting.infrastructure;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNotificationType;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "waiting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long storeId;

    @NotNull
    private int adult;

    @NotNull
    private int children;

    @NotNull
    private int number;

    @NotNull
    @Column(length = 20)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WaitingStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WaitingNotificationType notificationType;

    @NotNull
    private UUID uniqueCode;

    public static WaitingEntity from(final Waiting waiting) {
        final WaitingEntity waitingEntity = new WaitingEntity();
        waitingEntity.id = Objects.isNull(waiting.getId()) ? null : waiting.getId().value();
        waitingEntity.storeId = Objects.requireNonNull(waiting.getStoreId(), "웨이팅의 매장 ID는 null일 수 없습니다.").value();
        waitingEntity.adult = waiting.getAdult().value();
        waitingEntity.children = waiting.getChildren().value();
        waitingEntity.number = waiting.getNumber().value();
        waitingEntity.phoneNumber = waiting.getPhoneNumber().toString();
        waitingEntity.status = waiting.getStatus();
        waitingEntity.notificationType = waiting.getNotificationType();
        waitingEntity.uniqueCode = waiting.getUniqueCode();
        return waitingEntity;
    }

    public Waiting toModel() {
        return Waiting.builder()
            .id(new WaitingId(id))
            .storeId(new StoreId(storeId))
            .adult(new WaitingAdult(adult))
            .children(new WaitingChildren(children))
            .number(new WaitingNumber(number))
            .phoneNumber(new PhoneNumber(phoneNumber))
            .status(status)
            .notificationType(notificationType)
            .uniqueCode(uniqueCode)
            .timestamp(getDomainTimestamp())
            .build();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof final WaitingEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
