package com.felipe.admin.catalogo.application.genre.create;

import java.util.List;

public record CreateGenreCommand(String name, boolean isActive, List<String> categoriesIds) {
    public static CreateGenreCommand with(final String aName, final boolean isActive, List<String> categoriesIds) {
        return new CreateGenreCommand(aName, isActive, categoriesIds);
    }
}