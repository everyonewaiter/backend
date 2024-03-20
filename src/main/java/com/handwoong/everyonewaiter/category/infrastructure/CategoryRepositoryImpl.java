package com.handwoong.everyonewaiter.category.infrastructure;

import static com.handwoong.everyonewaiter.category.infrastructure.QCategoryEntity.categoryEntity;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

	private final JPAQueryFactory queryFactory;
	private final CategoryJpaRepository categoryJpaRepository;

	@Override
	public Category save(final Category category) {
		return categoryJpaRepository.save(CategoryEntity.from(category)).toModel();
	}

	@Override
	public Category findByIdOrElseThrow(final CategoryId id) {
		return categoryJpaRepository.findById(id.value())
				.orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다.", "id : [" + id + "]"))
				.toModel();
	}

	@Override
	public List<Category> findAllByStoreId(final StoreId storeId) {
		return queryFactory.selectFrom(categoryEntity)
				.where(categoryEntity.storeId.eq(storeId.value()))
				.fetch()
				.stream()
				.map(CategoryEntity::toModel)
				.toList();
	}

	@Override
	public void delete(final Category category) {
		categoryJpaRepository.delete(CategoryEntity.from(category));
	}

	@Override
	public boolean existsByIdAndStoreId(final CategoryId id, final StoreId storeId) {
		return Objects.nonNull(
				queryFactory.selectOne()
						.from(categoryEntity)
						.where(
								categoryEntity.id.eq(id.value()),
								storeIdEq(storeId)
						)
						.fetchFirst()
		);
	}

	private BooleanExpression storeIdEq(final StoreId storeId) {
		return Objects.isNull(storeId) ? null : categoryEntity.storeId.eq(storeId.value());
	}
}
