package com.felipe.admin.catalogo.infrastructure.genre;

import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.genre.GenreID;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;
import com.felipe.admin.catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import com.felipe.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import com.felipe.admin.catalogo.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class GenreMySqlGateway implements GenreGateway {

    private final GenreRepository repository;

    public GenreMySqlGateway(final GenreRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Genre create(Genre genre) {
        final var aGenre = this.repository.save(GenreJpaEntity.from(genre));
        return aGenre.toAggregate();
    }

    @Override
    public void deleteById(GenreID genreID) {
        final var aGenreId = genreID.getValue();
        if (this.repository.existsById(aGenreId)) {
            this.repository.deleteById(aGenreId);
        }
    }

    @Override
    public Optional<Genre> findById(GenreID genreID) {
        return repository.findById(genreID.getValue()).map(GenreJpaEntity::toAggregate);
    }

    @Override
    public Genre update(Genre genre) {
        return create(genre);
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery aQuery) {

        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.repository.findAll(where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(GenreJpaEntity::toAggregate).toList()
        );
    }
    private Specification<GenreJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }

    @Override
    public List<GenreID> existsByIds(Iterable<GenreID> genreIDS) {

        final var ids = StreamSupport.stream(genreIDS.spliterator(), false)
                .map(GenreID::getValue)
                .toList();

        return this.repository.existsByIds(ids).stream()
                .map(GenreID::from)
                .toList();
    }
}
