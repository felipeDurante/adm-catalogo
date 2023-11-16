package com.felipe.admin.catalogo.application.genre.create;

import com.felipe.admin.catalogo.domain.genre.Genre;

public record CreateGenreOutPut(
        String id
) {
    public static CreateGenreOutPut from(final String aGenre) {
        return new CreateGenreOutPut(aGenre);
    }

    public static CreateGenreOutPut from(final Genre anID) {
        return new CreateGenreOutPut(anID.getId().getValue());
    }
}
