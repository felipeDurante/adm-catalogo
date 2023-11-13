package com.felipe.admin.catalogo.application.category.update;

import com.felipe.admin.catalogo.application.UseCase;
import com.felipe.admin.catalogo.domain.exceptions.NotificationPattern;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<NotificationPattern, UpdateCategoryOutPut>> {
}
