package com.handwoong.everyonewaiter.menu.infrastructure;

import static com.handwoong.everyonewaiter.menu.domain.MenuDescription.MENU_DESCRIPTION_MAX_LENGTH;
import static com.handwoong.everyonewaiter.menu.domain.MenuName.MENU_NAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuDescription;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuName;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroups;
import com.handwoong.everyonewaiter.menu.domain.MenuSpicy;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.menu.domain.Money;
import com.handwoong.everyonewaiter.store.domain.StoreId;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long storeId;

	@NotNull
	private Long categoryId;

	@NotNull
	@Column(length = MENU_NAME_MAX_LENGTH)
	private String name;

	@NotNull
	@Column(length = MENU_DESCRIPTION_MAX_LENGTH)
	private String description;

	@NotNull
	private String image;

	@NotNull
	private long price;

	@NotNull
	private int spicy;

	private boolean printBillInKitchen;

	@Enumerated(EnumType.STRING)
	private MenuStatus status;

	@Enumerated(EnumType.STRING)
	private MenuLabel label;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "menu_id", nullable = false, updatable = false)
	private List<MenuOptionGroupEntity> optionGroupEntities = new ArrayList<>();

	public static MenuEntity from(final Menu menu) {
		final MenuEntity menuEntity = new MenuEntity();
		menuEntity.id = Objects.isNull(menu.getId()) ? null : menu.getId().value();
		menuEntity.storeId = Objects.requireNonNull(menu.getStoreId(), "메뉴의 매장 ID는 null일 수 없습니다.").value();
		menuEntity.categoryId = Objects.requireNonNull(menu.getCategoryId(), "메뉴의 카테고리 ID는 null일 수 없습니다.").value();
		menuEntity.name = menu.getName().toString();
		menuEntity.description = menu.getDescription().toString();
		menuEntity.image = menu.getImage();
		menuEntity.price = menu.getPrice().value();
		menuEntity.spicy = menu.getSpicy().value();
		menuEntity.printBillInKitchen = menu.isPrintBillInKitchen();
		menuEntity.status = menu.getStatus();
		menuEntity.label = menu.getLabel();
		menuEntity.optionGroupEntities = menu.getOptionGroups().toEntity();
		return menuEntity;
	}

	public Menu toModel() {
		return Menu.builder()
				.id(new MenuId(id))
				.storeId(new StoreId(storeId))
				.categoryId(new CategoryId(categoryId))
				.name(new MenuName(name))
				.description(new MenuDescription(description))
				.image(image)
				.price(new Money(price))
				.spicy(new MenuSpicy(spicy))
				.printBillInKitchen(printBillInKitchen)
				.status(status)
				.label(label)
				.optionGroups(
						new MenuOptionGroups(
								optionGroupEntities.stream()
										.map(MenuOptionGroupEntity::toModel)
										.toList()
						)
				)
				.build();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof final MenuEntity that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
