package com.handwoong.everyonewaiter.notification.infrastructure;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.notification.domain.Notification;
import com.handwoong.everyonewaiter.notification.domain.NotificationId;
import com.handwoong.everyonewaiter.notification.domain.Template;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long storeId;

	@NotNull
	private Template template;

	@NotNull
	private String requestId;

	@NotNull
	private String requestTime;

	@NotNull
	private String statusCode;

	@NotNull
	private String statusName;

	@NotNull
	private String messageId;

	@NotNull
	@Column(length = 20)
	private String phoneNumber;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String content;

	@NotNull
	private String requestStatusCode;

	@NotNull
	private String requestStatusName;

	@NotNull
	private String requestStatusDesc;

	private boolean useSmsFailover;

	public static NotificationEntity from(final Notification notification) {
		final NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.id = Objects.isNull(notification.getId()) ? null : notification.getId().value();
		notificationEntity.storeId = Objects.requireNonNull(notification.getStoreId(), "매장 ID는 null일 수 없습니다.").value();
		notificationEntity.template = notification.getTemplate();
		notificationEntity.requestId = notification.getRequestId();
		notificationEntity.requestTime = notification.getRequestTime();
		notificationEntity.statusCode = notification.getStatusCode();
		notificationEntity.statusName = notification.getStatusName();
		notificationEntity.messageId = notification.getMessageId();
		notificationEntity.phoneNumber = notification.getPhoneNumber().toString();
		notificationEntity.content = notification.getContent();
		notificationEntity.requestStatusCode = notification.getRequestStatusCode();
		notificationEntity.requestStatusName = notification.getRequestStatusName();
		notificationEntity.requestStatusDesc = notification.getRequestStatusDesc();
		notificationEntity.useSmsFailover = notification.isUseSmsFailover();
		return notificationEntity;
	}

	public Notification toModel() {
		return Notification.builder()
				.id(new NotificationId(id))
				.storeId(new StoreId(storeId))
				.template(template)
				.requestId(requestId)
				.requestTime(requestTime)
				.statusCode(statusCode)
				.statusName(statusName)
				.messageId(messageId)
				.phoneNumber(new PhoneNumber(phoneNumber))
				.content(content)
				.requestStatusCode(requestStatusCode)
				.requestStatusName(requestStatusName)
				.requestStatusDesc(requestStatusDesc)
				.useSmsFailover(useSmsFailover)
				.build();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof final NotificationEntity that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
