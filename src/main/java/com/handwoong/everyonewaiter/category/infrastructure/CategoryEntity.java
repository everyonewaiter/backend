package com.handwoong.everyonewaiter.category.infrastructure;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
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
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long storeId;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    @Column(length = 20)
    private String icon;

    public static CategoryEntity from(final Category category) {
        final CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.id = Objects.isNull(category.getId()) ? null : category.getId().value();
        categoryEntity.storeId = Objects.requireNonNull(category.getStoreId(), "카테고리의 매장 ID는 null일 수 없습니다.").value();
        categoryEntity.name = category.getName().toString();
        categoryEntity.icon = category.getIcon();
        return categoryEntity;
    }

    public Category toModel() {
        return Category.builder()
            .id(new CategoryId(id))
            .storeId(new StoreId(storeId))
            .name(new CategoryName(name))
            .icon(icon)
            .timestamp(getDomainTimestamp())
            .build();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof final CategoryEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
