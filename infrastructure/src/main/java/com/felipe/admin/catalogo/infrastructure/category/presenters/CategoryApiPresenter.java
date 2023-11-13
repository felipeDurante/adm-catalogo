package com.felipe.admin.catalogo.infrastructure.category.presenters;

import com.felipe.admin.catalogo.application.category.find.get.CategoryOutPut;
import com.felipe.admin.catalogo.application.category.find.list.CategoryListOutput;
import com.felipe.admin.catalogo.infrastructure.category.models.CategoryApiResponse;
import com.felipe.admin.catalogo.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {

    static CategoryApiResponse present(final CategoryOutPut outPut) {
        return new CategoryApiResponse(
                outPut.id().getValue(),
                outPut.name(),
                outPut.description(),
                outPut.isActive(),
                outPut.createdAt(),
                outPut.updatedAt(),
                outPut.deletedAt());
    }

    static CategoryListResponse present(final CategoryListOutput outPut) {
        return new CategoryListResponse(
                outPut.id().getValue(),
                outPut.name(),
                outPut.description(),
                outPut.isActive(),
                outPut.createdAt(),
                outPut.deletedAt());
    }
}
