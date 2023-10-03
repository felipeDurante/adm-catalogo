package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.exceptions.NotificationPattern;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationPattern, CreateCategoryOutPut> execute(final CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = NotificationPattern.create();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);
        aCategory.validate(notification);

        return notification.hasError() ? API.Left(notification) : create(aCategory); // se tiver erro devolve do tipo notification com todos os seus erros
    }

    private Either<NotificationPattern, CreateCategoryOutPut> create(final Category aCategory) {
//        API.Try(() -> this.categoryGateway.create(aCategory))
//                .toEither()
//                .map(CreateCategoryOutPut::from)
//                .mapLeft(NotificationPattern::create);
        //or
        return API.Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(NotificationPattern::create, CreateCategoryOutPut::from); // se tiver algum erro mapeia para o notificiation se nao a saida e a direita como padrao

//        return API.Right(CreateCategoryOutPut.from(this.categoryGateway.create(aCategory)));
    }


}
