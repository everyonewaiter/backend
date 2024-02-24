package com.handwoong.everyonewaiter.util;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.Category.CategoryBuilder;
import com.handwoong.everyonewaiter.category.domain.CategoryIcon;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakeUuidHolder;
import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.Store.StoreBuilder;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime.StoreBreakTimeBuilder;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime.StoreBusinessTimeBuilder;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.domain.StoreOption.StoreOptionBuilder;
import com.handwoong.everyonewaiter.store.domain.StoreOptionId;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.User.UserBuilder;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.Waiting.WaitingBuilder;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNotificationType;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Fixtures {

	private Fixtures() {
	}

	public static UserBuilder aUser() {
		return User.builder()
				.id(new UserId(1L))
				.username(new Username("handwoong"))
				.password(new Password("password"))
				.phoneNumber(new PhoneNumber("01012345678"))
				.role(UserRole.ROLE_USER)
				.status(UserStatus.ACTIVE);
	}

	public static StoreBuilder aStore() {
		return Store.builder()
				.id(new StoreId(1L))
				.userId(new UserId(1L))
				.name(new StoreName("나루"))
				.landlineNumber(new LandlineNumber("0551234567"))
				.status(StoreStatus.OPEN)
				.lastOpenedAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0))
				.businessTimes(new StoreBusinessTimes(List.of(aStoreBusinessTime().build())))
				.breakTimes(
						new StoreBreakTimes(
								List.of(
										aStoreBreakTime().build(),
										aStoreBreakTime()
												.id(new StoreBreakTimeId(2L))
												.start(LocalTime.of(15, 30, 0))
												.end(LocalTime.of(17, 0, 0))
												.daysOfWeek(aWeekend())
												.build()
								)
						)
				)
				.option(aStoreOption().build());
	}

	public static StoreBreakTimeBuilder aStoreBreakTime() {
		return StoreBreakTime.builder()
				.id(new StoreBreakTimeId(1L))
				.start(LocalTime.of(15, 0, 0))
				.end(LocalTime.of(16, 30, 0))
				.daysOfWeek(aWeekday());
	}

	public static StoreBusinessTimeBuilder aStoreBusinessTime() {
		return StoreBusinessTime.builder()
				.id(new StoreBusinessTimeId(1L))
				.open(LocalTime.of(11, 0, 0))
				.close(LocalTime.of(21, 0, 0))
				.daysOfWeek(anAllDay());
	}

	public static StoreDaysOfWeek aWeekday() {
		return new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));
	}

	public static StoreDaysOfWeek aWeekend() {
		return new StoreDaysOfWeek(List.of(SATURDAY, SUNDAY));
	}

	public static StoreDaysOfWeek anAllDay() {
		return new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY));
	}

	public static StoreOptionBuilder aStoreOption() {
		return StoreOption.builder()
				.id(new StoreOptionId(1L))
				.useBreakTime(true)
				.useWaiting(true)
				.useOrder(true);
	}

	public static WaitingBuilder aWaiting() {
		final FakeUuidHolder uuidHolder = new FakeUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
		return Waiting.builder()
				.id(new WaitingId(1L))
				.storeId(new StoreId(1L))
				.adult(new WaitingAdult(2))
				.children(new WaitingChildren(0))
				.number(new WaitingNumber(10))
				.phoneNumber(new PhoneNumber("01012345678"))
				.status(WaitingStatus.WAIT)
				.notificationType(WaitingNotificationType.REGISTER)
				.uniqueCode(uuidHolder.generate())
				.timestamp(
						DomainTimestamp.builder()
								.createdAt(LocalDateTime.now())
								.build()
				);
	}

	public static CategoryBuilder aCategory() {
		return Category.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(1L))
				.name(new CategoryName("스테이크"))
				.icon(new CategoryIcon("drumstick"));
	}
}
