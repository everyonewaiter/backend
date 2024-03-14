package com.handwoong.everyonewaiter.menu.infrastructure;

import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.menu.domain.MenuMultiSelectOption;
import com.handwoong.everyonewaiter.menu.domain.MenuMultiSelectOptionId;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionName;
import com.handwoong.everyonewaiter.menu.domain.Money;
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
@Table(name = "menu_multi_select_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuMultiSelectOptionEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = MENU_OPTION_NAME_MAX_LENGTH)
	private String name;

	@NotNull
	private long price;

	public static MenuMultiSelectOptionEntity from(final MenuMultiSelectOption menuMultiSelectOption) {
		final MenuMultiSelectOptionEntity menuMultiSelectOptionEntity = new MenuMultiSelectOptionEntity();
		menuMultiSelectOptionEntity.id =
				Objects.isNull(menuMultiSelectOption.getId()) ? null : menuMultiSelectOption.getId().value();
		menuMultiSelectOptionEntity.name = menuMultiSelectOption.getName().toString();
		menuMultiSelectOptionEntity.price = menuMultiSelectOption.getPrice().value();
		return menuMultiSelectOptionEntity;
	}

	public MenuMultiSelectOption toModel() {
		return MenuMultiSelectOption.builder()
				.id(new MenuMultiSelectOptionId(id))
				.name(new MenuOptionName(name))
				.price(new Money(price))
				.build();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof final MenuMultiSelectOptionEntity that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
