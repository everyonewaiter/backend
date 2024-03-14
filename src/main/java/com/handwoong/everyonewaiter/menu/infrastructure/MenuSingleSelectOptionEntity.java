package com.handwoong.everyonewaiter.menu.infrastructure;

import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionName;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOption;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOptionId;
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
@Table(name = "menu_single_select_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSingleSelectOptionEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = MENU_OPTION_NAME_MAX_LENGTH)
	private String name;

	@NotNull
	private long price;

	@NotNull
	private boolean isDefault;

	public static MenuSingleSelectOptionEntity from(final MenuSingleSelectOption menuSingleSelectOption) {
		final MenuSingleSelectOptionEntity menuSingleSelectOptionEntity = new MenuSingleSelectOptionEntity();
		menuSingleSelectOptionEntity.id =
				Objects.isNull(menuSingleSelectOption.getId()) ? null : menuSingleSelectOption.getId().value();
		menuSingleSelectOptionEntity.name = menuSingleSelectOption.getName().toString();
		menuSingleSelectOptionEntity.price = menuSingleSelectOption.getPrice().value();
		menuSingleSelectOptionEntity.isDefault = menuSingleSelectOption.isDefault();
		return menuSingleSelectOptionEntity;
	}

	public MenuSingleSelectOption toModel() {
		return MenuSingleSelectOption.builder()
				.id(new MenuSingleSelectOptionId(id))
				.name(new MenuOptionName(name))
				.price(new Money(price))
				.isDefault(isDefault)
				.build();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof final MenuSingleSelectOptionEntity that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
