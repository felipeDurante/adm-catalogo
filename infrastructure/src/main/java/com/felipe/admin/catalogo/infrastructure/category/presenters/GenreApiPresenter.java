package com.felipe.admin.catalogo.infrastructure.category.presenters;

import com.felipe.admin.catalogo.application.category.find.get.CategoryOutPut;
import com.felipe.admin.catalogo.application.category.find.list.CategoryListOutput;
import com.felipe.admin.catalogo.application.genre.find.get.GenreOutPut;
import com.felipe.admin.catalogo.application.genre.find.list.GenreListOutPut;
import com.felipe.admin.catalogo.infrastructure.category.models.CategoryApiResponse;
import com.felipe.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import com.felipe.admin.catalogo.infrastructure.genre.models.GenreApiResponse;
import com.felipe.admin.catalogo.infrastructure.genre.models.GenreListResponse;

public interface GenreApiPresenter {

    static GenreApiResponse present(final GenreOutPut outPut) {
        return new GenreApiResponse(
                outPut.id(),
                outPut.name(),
                outPut.categories(),
                outPut.isActive(),
                outPut.createdAt(),
                outPut.updatedAt(),
                outPut.deletedAt());
    }

    static GenreListResponse present(final GenreListOutPut outPut) {
        return new GenreListResponse(
                outPut.id(),
                outPut.name(),
                outPut.isActive(),
                outPut.createdAt(),
                outPut.deletedAt());
    }
}
