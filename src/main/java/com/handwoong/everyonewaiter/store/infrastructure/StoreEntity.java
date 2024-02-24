package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.UserId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long userId;

	@NotNull
	@Column(length = STORE_NAME_MAX_LENGTH)
	private String name;

	@NotNull
	@Column(length = 20)
	private String landlineNumber;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StoreStatus status;

	private LocalDateTime lastOpenedAt;

	private LocalDateTime lastClosedAt;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "store_id", nullable = false, updatable = false)
	private List<StoreBusinessTimeEntity> businessTimeEntities = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "store_id", nullable = false, updatable = false)
	private List<StoreBreakTimeEntity> breakTimeEntities = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "store_option_id")
	private StoreOptionEntity optionEntity;

	public static StoreEntity from(final Store store) {
		final StoreEntity storeEntity = new StoreEntity();
		storeEntity.id = Objects.isNull(store.getId()) ? null : store.getId().value();
		storeEntity.userId = Objects.requireNonNull(store.getUserId(), "매장의 사용자 ID는 null일 수 없습니다.").value();
		storeEntity.name = store.getName().toString();
		storeEntity.landlineNumber = store.getLandlineNumber().toString();
		storeEntity.status = store.getStatus();
		storeEntity.lastOpenedAt = store.getLastOpenedAt();
		storeEntity.lastClosedAt = store.getLastClosedAt();
		storeEntity.businessTimeEntities = store.getBusinessTimes().toEntity();
		storeEntity.breakTimeEntities = store.getBreakTimes().toEntity();
		storeEntity.optionEntity = StoreOptionEntity.from(store.getOption());
		return storeEntity;
	}

	public Store toModel() {
		return Store.builder()
				.id(new StoreId(id))
				.userId(new UserId(userId))
				.name(new StoreName(name))
				.landlineNumber(new LandlineNumber(landlineNumber))
				.status(status)
				.lastOpenedAt(lastOpenedAt)
				.lastClosedAt(lastClosedAt)
				.businessTimes(
						new StoreBusinessTimes(
								businessTimeEntities.stream()
										.map(StoreBusinessTimeEntity::toModel)
										.toList()
						)
				)
				.breakTimes(
						new StoreBreakTimes(
								breakTimeEntities.stream()
										.map(StoreBreakTimeEntity::toModel)
										.toList()
						)
				)
				.option(optionEntity.toModel())
				.timestamp(getDomainTimestamp())
				.build();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final StoreEntity that)) {
			return false;
		}
		return Objects.nonNull(id) && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
