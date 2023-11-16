package com.felipe.admin.catalogo.application.genre.find.list;

import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreListOutPut(
        String id,
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant deletedAt
) {

    public static GenreListOutPut from(final Genre aGenre) {
        return new GenreListOutPut(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream()
                        .map(CategoryID::getValue)
                        .toList(),
                aGenre.getCreatedAt(),
                aGenre.getDeletedAt()
        );
    }
}