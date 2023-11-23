package com.felipe.admin.catalogo.application.genre.create;

import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.NotificationException;
import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;
import com.felipe.admin.catalogo.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase {

    private final GenreGateway genreGateway;
    private final CategoryGateway categoryGateway;

    public DefaultCreateGenreUseCase(final GenreGateway genreGateway,final CategoryGateway categoryGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateGenreOutPut execute(CreateGenreCommand aCommand) {

        final var aName = aCommand.name();
        final var isActive = aCommand.isActive();
        final var categories = toCategoryId(aCommand.categories());

        final var notification = Notification.create();

        notification.append(validateCategories(categories));

        final var aGenre = notification.validate(() -> Genre.newGenre(aName, isActive));

        if (notification.hasError())
            throw new NotificationException("Could not create Aggregate Genre", notification);

        categories.forEach(aGenre::addCategory);

        return CreateGenreOutPut.from(this.genreGateway.create(aGenre));
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

    private List<CategoryID> toCategoryId(List<String> categories) {
        return categories.stream()
                .map(CategoryID::from)
                .collect(Collectors.toList());
    }
}
