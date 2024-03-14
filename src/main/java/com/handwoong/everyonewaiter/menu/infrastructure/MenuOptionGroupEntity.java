package com.handwoong.everyonewaiter.menu.infrastructure;

import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.menu.domain.MenuMultiSelectOptions;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroup;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroupId;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionName;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOptions;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "menu_option_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOptionGroupEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = MENU_OPTION_NAME_MAX_LENGTH)
	private String name;

	@NotNull
	private boolean useOptionPrice;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "menu_option_group_id", nullable = false, updatable = false)
	private List<MenuSingleSelectOptionEntity> singleSelectOptionEntities = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "menu_option_group_id", nullable = false, updatable = false)
	private List<MenuMultiSelectOptionEntity> multiSelectOptionEntities = new ArrayList<>();

	public static MenuOptionGroupEntity from(final MenuOptionGroup menuOptionGroup) {
		final MenuOptionGroupEntity menuOptionGroupEntity = new MenuOptionGroupEntity();
		menuOptionGroupEntity.id = Objects.isNull(menuOptionGroup.getId()) ? null : menuOptionGroup.getId().value();
		menuOptionGroupEntity.name = menuOptionGroup.getName().toString();
		menuOptionGroupEntity.useOptionPrice = menuOptionGroup.isUseOptionPrice();
		menuOptionGroupEntity.singleSelectOptionEntities = menuOptionGroup.getSingleSelectOptions().toEntity();
		menuOptionGroupEntity.multiSelectOptionEntities = menuOptionGroup.getMultiSelectOptions().toEntity();
		return menuOptionGroupEntity;
	}

	public MenuOptionGroup toModel() {
		return MenuOptionGroup.builder()
				.id(new MenuOptionGroupId(id))
				.name(new MenuOptionName(name))
				.useOptionPrice(useOptionPrice)
				.singleSelectOptions(
						new MenuSingleSelectOptions(
								singleSelectOptionEntities.stream()
										.map(MenuSingleSelectOptionEntity::toModel)
										.toList()
						)
				)
				.multiSelectOptions(
						new MenuMultiSelectOptions(
								multiSelectOptionEntities.stream()
										.map(MenuMultiSelectOptionEntity::toModel)
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
		if (!(object instanceof final MenuOptionGroupEntity that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
