package com.felipe.admin.catalogo.application.category.delete;

import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    @Override
    public void execute(final String anIn) {

        this.categoryGateway.deleteById(CategoryID.from(anIn));

    }
}
