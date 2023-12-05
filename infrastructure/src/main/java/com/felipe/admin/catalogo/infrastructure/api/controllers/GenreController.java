package com.felipe.admin.catalogo.infrastructure.api.controllers;


import com.felipe.admin.catalogo.application.genre.create.CreateGenreCommand;
import com.felipe.admin.catalogo.application.genre.create.CreateGenreUseCase;
import com.felipe.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.felipe.admin.catalogo.application.genre.find.get.GetGenreByIdUseCase;
import com.felipe.admin.catalogo.application.genre.find.list.ListGenreUseCase;
import com.felipe.admin.catalogo.application.genre.update.UpdateGenreCommand;
import com.felipe.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;
import com.felipe.admin.catalogo.infrastructure.api.GenreAPI;
import com.felipe.admin.catalogo.infrastructure.category.presenters.GenreApiPresenter;
import com.felipe.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.felipe.admin.catalogo.infrastructure.genre.models.GenreApiResponse;
import com.felipe.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import com.felipe.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class GenreController implements GenreAPI {


    private final CreateGenreUseCase createGenreUseCase;

    private final UpdateGenreUseCase updateGenreUseCase;

    private final DeleteGenreUseCase deleteGenreUseCase;

    private final GetGenreByIdUseCase getGenreByIdUseCase;

    private final ListGenreUseCase listGenreUseCase;

    public GenreController(final CreateGenreUseCase createGenreUseCase,
                           final UpdateGenreUseCase updateGenreUseCase,
                           final DeleteGenreUseCase deleteGenreUseCase,
                           final GetGenreByIdUseCase getGenreByIdUseCase,
                           final ListGenreUseCase listGenreUseCase

    ) {
        this.createGenreUseCase = Objects.requireNonNull(createGenreUseCase);
        this.updateGenreUseCase = Objects.requireNonNull(updateGenreUseCase);
        this.deleteGenreUseCase = Objects.requireNonNull(deleteGenreUseCase);
        this.getGenreByIdUseCase = Objects.requireNonNull(getGenreByIdUseCase);
        this.listGenreUseCase = Objects.requireNonNull(listGenreUseCase);
//        this.listGenreUseCase = Objects.requireNonNull(listGenreUseCase);
    }


    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {

        final var aCommand = CreateGenreCommand.with(
                input.name(),
                input.active(),
                input.categories()
        );

        final var output = this.createGenreUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);

    }

    @Override
    public GenreApiResponse getById(final String id) {

        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }


    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {
        var aCommand = UpdateGenreCommand.with(
                id,
                input.name(),
                input.active(),
                input.categories());

        var response = updateGenreUseCase.execute(aCommand);

        return ResponseEntity.ok(response);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteGenreUseCase.execute(id);
    }

    @Override
    public Pagination<GenreListResponse> list(final String search, final int page, final int perPage, final String sort, final String direction) {
//        return null;
        return this.listGenreUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(GenreApiPresenter::present);
    }
}
