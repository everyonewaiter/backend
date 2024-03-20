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
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.Menu.MenuBuilder;
import com.handwoong.everyonewaiter.menu.domain.MenuDescription;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuMultiSelectOptions;
import com.handwoong.everyonewaiter.menu.domain.MenuName;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroup;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroup.MenuOptionGroupBuilder;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroupId;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroups;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionName;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOption;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOptionId;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOptions;
import com.handwoong.everyonewaiter.menu.domain.MenuSpicy;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.menu.domain.Money;
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

	public static MenuBuilder aMenu() {
		return Menu.builder()
				.id(new MenuId(1L))
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(1L))
				.name(new MenuName("수비드 소고기 스테이크"))
				.description(new MenuDescription("부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크"))
				.image("")
				.price(new Money(29_900))
				.spicy(new MenuSpicy(0))
				.printBillInKitchen(true)
				.status(MenuStatus.BASIC)
				.label(MenuLabel.REPRESENT)
				.optionGroups(new MenuOptionGroups(List.of(aMenuOptionGroup().build())));
	}

	public static MenuOptionGroupBuilder aMenuOptionGroup() {
		return MenuOptionGroup.builder()
				.id(new MenuOptionGroupId(1L))
				.name(new MenuOptionName("맵기 조절"))
				.singleSelectOptions(
						new MenuSingleSelectOptions(
								List.of(
										MenuSingleSelectOption.builder()
												.id(new MenuSingleSelectOptionId(1L))
												.name(new MenuOptionName("안맵게"))
												.price(new Money(0))
												.isDefault(false)
												.build(),
										MenuSingleSelectOption.builder()
												.id(new MenuSingleSelectOptionId(2L))
												.name(new MenuOptionName("기본"))
												.price(new Money(0))
												.isDefault(true)
												.build(),
										MenuSingleSelectOption.builder()
												.id(new MenuSingleSelectOptionId(3L))
												.name(new MenuOptionName("맵게"))
												.price(new Money(0))
												.isDefault(false)
												.build()
								)
						)
				)
				.multiSelectOptions(new MenuMultiSelectOptions(List.of()))
				.useOptionPrice(false);
	}
}
