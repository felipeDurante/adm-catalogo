package com.felipe.admin.catalogo.domain.category;

import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID andID);

    Optional<Category> findById(CategoryID categoryID);

    Category update(Category Acategory);

//    Pagination<Category> findAll(CategorySearchQuery aQuery);

    Pagination<Category> findAll(SearchQuery aQuery);

    List<CategoryID> existsByIds(Iterable<CategoryID> ids);

}
