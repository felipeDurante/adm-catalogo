package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutPut execute(final CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);

        aCategory.validate(new ThrowsValidationHandler());

        return CreateCategoryOutPut.from(this.categoryGateway.create(aCategory));
    }
}
