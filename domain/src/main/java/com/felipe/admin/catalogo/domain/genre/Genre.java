package com.felipe.admin.catalogo.domain.genre;

import com.felipe.admin.catalogo.domain.AggregateRoot;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.NotificationException;
import com.felipe.admin.catalogo.domain.utils.InstantUtils;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;
import com.felipe.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Genre extends AggregateRoot<GenreID> {

    private String name;
    private boolean active;

    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private List<CategoryID> categories;

    private Genre(
            final GenreID andId,
            final String name,
            final boolean active,
            final List<CategoryID> categories,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        super(andId);
        this.name = name;
        this.active = active;
        this.categories = categories;
        this.createdAt = Objects.requireNonNull(createdAt, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "'updatedAt' should not be null");
        this.deletedAt = deletedAt;

        selfValidate();
    }

    public static Genre newGenre(final String name, final boolean isActive) {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final var id = GenreID.unique();
        final var now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        final var deletedAt = isActive ? null : now;
        return new Genre(id, name, isActive, new ArrayList<>(), now, now, deletedAt);
    }


    public static Genre with(
            final GenreID anId,
            final String name,
            final boolean active,
            final List<CategoryID> categories,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Genre(
                anId,
                name,
                active,
                categories,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Genre with(final Genre aGenre) {
        return new Genre(
                aGenre.id,
                aGenre.name,
                aGenre.active,
                new ArrayList<>(aGenre.categories),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);
        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate Genre", notification);
        }
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }


    public Genre update(final String aName, final boolean isActive, final List<CategoryID> categoryIDS) {
        if (isActive)
            activate();
        else
            deactivate();

        this.name = aName;
        this.updatedAt = InstantUtils.now();
        this.categories = new ArrayList<>(categoryIDS != null ? categoryIDS : Collections.emptyList());
        selfValidate();
        return this;
    }

    public Genre activate() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre deactivate() {

        if (getDeletedAt() == null)
            this.deletedAt = InstantUtils.now();

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }
    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Genre addCategory(final CategoryID aCategoryID) {
        if (aCategoryID == null) {
            return this;
        }
        this.categories.add(aCategoryID);
        this.updatedAt = InstantUtils.now();
        return this;
    }
    public Genre addCategories(final List<CategoryID> categories) {
        if (categories == null || categories.isEmpty()) {
            return this;
        }
        this.categories.addAll(categories);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre removeCategory(final CategoryID aCategoryID) {

        if (aCategoryID == null) {
            return this;
        }
        this.categories.remove(aCategoryID);
        this.updatedAt = InstantUtils.now();
        return this;
    }
}
