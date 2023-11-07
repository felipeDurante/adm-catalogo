package com.felipe.admin.catalogo.infrastructure.category;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.category.CategorySearchQuery;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryMySqlGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    public CategoryMySqlGateway(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category aCategory) {
        return this.categoryRepository.save(CategoryEntity.from(aCategory)).toAggregate();
    }

    @Override
    public void deleteById(CategoryID andID) {

        var andId = andID.getValue();
        if (categoryRepository.existsById(andId))
            categoryRepository.deleteById(andId);

    }

    @Override
    public Optional<Category> findById(CategoryID categoryID) {
        return categoryRepository.findById(categoryID.getValue()).map(CategoryEntity::toAggregate);
    }

    @Override
    public Category update(Category aCategory) {

        return this.categoryRepository.save(CategoryEntity.from(aCategory)).toAggregate();
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(SearchQuery aQuery) {
        return null;
    }

    @Override
    public List<CategoryID> existsByIds(Iterable<CategoryID> ids) {
        return null;
    }
}
