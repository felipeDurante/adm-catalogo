package com.felipe.admin.catalogo.infrastructure.api.controllers;

import com.felipe.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.felipe.admin.catalogo.application.category.create.CreateCategoryOutPut;
import com.felipe.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.felipe.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.get.GetCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.list.ListCategoriesUseCase;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryCommand;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryOutPut;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.felipe.admin.catalogo.domain.exceptions.NotificationPattern;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;
import com.felipe.admin.catalogo.infrastructure.api.CategoryAPI;
import com.felipe.admin.catalogo.infrastructure.category.models.CategoryApiResponse;
import com.felipe.admin.catalogo.infrastructure.category.models.CreateCategoryRequest;
import com.felipe.admin.catalogo.infrastructure.category.models.UpdateCategoryRequest;
import com.felipe.admin.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryUseCase getCategoryUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase, ListCategoriesUseCase listCategoriesUseCase) {

        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryUseCase = Objects.requireNonNull(getCategoryUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {

        var aCommand = CreateCategoryCommand.with(input.name(),
                input.description(),
                input.active() != null ? input.active() : true);

        final Function<NotificationPattern, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

//        final Function<NotificationPattern, ResponseEntity<?>> teste = ResponseEntity.unprocessableEntity()::body;

//        final Function<NotificationPattern, ResponseEntity<?>> onError =
//                notification ->
//                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutPut, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);

//        return result;
    }


    @Override
    public CategoryApiResponse getCategory(final String id) {
        var response = CategoryApiPresenter.present(getCategoryUseCase.execute(id));
        System.out.println("Response: " + response);

        return response;
    }

    @Override
    public ResponseEntity<?> updateCategoryById(final String id, final UpdateCategoryRequest input) {
        var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true);

        //existem 2 tipos de respostas, com erro ou sem
        //quando é executado é retornado ou um ou outro (Padrao Either)
        final Function<NotificationPattern, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutPut, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }

    @Override
    public Pagination<?> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String dir) {

        return this.listCategoriesUseCase.execute(new SearchQuery(page, perPage, search, sort, dir))
                .map(CategoryApiPresenter::present);

    }
}
