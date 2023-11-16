package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.application.UseCase;
import com.felipe.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification,CreateCategoryOutPut>> {
}
