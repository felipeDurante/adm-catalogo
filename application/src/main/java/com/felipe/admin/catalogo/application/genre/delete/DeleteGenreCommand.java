package com.felipe.admin.catalogo.application.genre.delete;

public record DeleteGenreCommand(String id) {
    public static DeleteGenreCommand with(String id) {
        return new DeleteGenreCommand(id);
    }
}
