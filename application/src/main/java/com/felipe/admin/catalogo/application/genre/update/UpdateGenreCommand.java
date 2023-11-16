package com.felipe.admin.catalogo.application.genre.update;
import java.util.List;

public record UpdateGenreCommand(
        String id,
        String name,
        boolean isActive,
        List<String> categories) {
    public static UpdateGenreCommand with(
            final String andID,
            final String aName,
            final boolean isActive,
            final List<String> categories) {
        return new UpdateGenreCommand(andID, aName, isActive, categories);

    }
}
