package com.felipe.admin.catalogo.application.genre.find.get;

import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreOutPut(String id,
                          String name,
                          boolean isActive,
                          List<String> categories,
                          Instant createdAt,
                          Instant updatedAt,
                          Instant deletedAt
) {

    public static GenreOutPut from(final Genre aGenre) {
        return new GenreOutPut(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream()
                        .map(CategoryID::getValue)
                        .toList(),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt()
        );
    }
}
