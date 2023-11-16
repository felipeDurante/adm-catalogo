package com.felipe.admin.catalogo.application.genre.update;

import com.felipe.admin.catalogo.domain.genre.Genre;

public record UpdateGenreOutPut(String id) {
    public static UpdateGenreOutPut from(final String id) {
        return new UpdateGenreOutPut(id);
    }     public static UpdateGenreOutPut from(final Genre id) {
        return new UpdateGenreOutPut(id.getId().getValue());
    }
}