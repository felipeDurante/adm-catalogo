package com.felipe.admin.catalogo.application.genre.update;

import com.felipe.admin.catalogo.domain.Identifier;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.DomainException;
import com.felipe.admin.catalogo.domain.exceptions.NotFoundException;
import com.felipe.admin.catalogo.domain.exceptions.NotificationException;
import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.genre.GenreID;
import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;
import com.felipe.admin.catalogo.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultUpdateGenreUseCase extends UpdateGenreUseCase {


    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultUpdateGenreUseCase(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public UpdateGenreOutPut execute(final UpdateGenreCommand aComand) {

        final var anId = GenreID.from(aComand.id());

        final var aName = aComand.name();

        final var isActive = aComand.isActive();

        final var categories = aComand.categories().stream().map(CategoryID::from).toList();

        var aGenre = this.genreGateway.findById(anId).orElseThrow(notFound(anId));

//        final var aGenre = this.genreGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        notification.append(validateCategories(categories));

        notification.validate(() -> aGenre.update(aName, isActive, categories));

        if (notification.hasError())
            throw new NotificationException("Could not update Aggregate Genre", notification);

        return UpdateGenreOutPut.from(this.genreGateway.update(aGenre));
//        return UpdateGenreOutPut.from(aGenre.update(aName, isActive, categories));

    }

    private Supplier<DomainException> notFound(final Identifier anId) {
        return () -> NotFoundException.with(Genre.class, anId);
    }

    private ValidationHandler validateCategories(final List<CategoryID> categoryIDS) {
        final var notification = Notification.create();

        if (categoryIDS == null || categoryIDS.isEmpty())
            return notification;

        final var foundIds = categoryGateway.existsByIds(categoryIDS);

        if (categoryIDS.size() != foundIds.size()) {
            final var commandIds = new ArrayList<>(categoryIDS); // cria uma copia da lista para que possa alterar o resultado
            commandIds.removeAll(foundIds); // só sobraram os ids que foram encontrados (validos)

            final var missingIds = commandIds
                    .stream()
                    .map(CategoryID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIds))); // coloca na notificacao os ids não encontrados
        }

        return notification;
    }
}
