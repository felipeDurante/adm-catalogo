package com.felipe.admin.catalogo.application.genre.find.list;


import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListGenreByIdUseCase extends ListGenreUseCase {

    private final GenreGateway genreGateway;

    public DefaultListGenreByIdUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<GenreListOutPut> execute(SearchQuery aQuery) {
        return this.genreGateway.findAll(aQuery)
                .map(GenreListOutPut::from);
    }
}
