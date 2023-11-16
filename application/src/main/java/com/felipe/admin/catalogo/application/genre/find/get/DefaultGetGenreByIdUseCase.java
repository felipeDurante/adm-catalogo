package com.felipe.admin.catalogo.application.genre.find.get;

import com.felipe.admin.catalogo.domain.exceptions.NotFoundException;
import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.genre.GenreID;

import java.util.Objects;

public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase {

    private final GenreGateway genreGateway;
    public DefaultGetGenreByIdUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public GenreOutPut execute(final String anID) {

        final var aGenreId = GenreID.from(anID);

//        var aGenre = genreGateway.findById(aGenreId)
//                .orElseThrow(() -> NotFoundException.with(Genre.class, aGenreId));
//
//        return GenreOutPut.from(aGenre);

         // ou

        return this.genreGateway.findById(aGenreId)
                .map(GenreOutPut::from)
                .orElseThrow(() -> NotFoundException.with(Genre.class, aGenreId));

    }
}
