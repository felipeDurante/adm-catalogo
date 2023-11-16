package com.felipe.admin.catalogo.application.category.update;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.DomainException;
import com.felipe.admin.catalogo.domain.validation.handler.Notification;
import com.felipe.admin.catalogo.domain.validation.Error;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutPut> execute(UpdateCategoryCommand aCommand) {
        final var andId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(andId).orElseThrow(() ->
                DomainException.with(new Error("Category with ID %s was not found".formatted(andId.getValue()))));

        final var notification = Notification.create();

//        final var aCategory = Category.update(aName, aDescription, isActive);
        aCategory.
                update(aName, aDescription, isActive).
                validate(notification);
//        aCategory.validate(notification);

        return notification.hasError() ? API.Left(notification) : update(aCategory); // s
    }

    private Either<Notification, UpdateCategoryOutPut> update(Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutPut::from);//create aqui é da notificacao
    }
}

