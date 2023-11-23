package com.felipe.admin.catalogo.infrastructure.genre;

import com.felipe.admin.catalogo.MySqlGatewayTest;
import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreID;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;
import com.felipe.admin.catalogo.infrastructure.category.CategoryMySqlGateway;
import com.felipe.admin.catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import com.felipe.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@MySqlGatewayTest
public class GenreMySQLGatewayTest {

    @Autowired
    private CategoryMySqlGateway categoryMySqlGateway;

    @Autowired
    private GenreMySqlGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testDependencyInjected() {
        Assertions.assertNotNull(categoryMySqlGateway);
        Assertions.assertNotNull(genreGateway);
        Assertions.assertNotNull(genreRepository);
    }

    final String expectedName = "Filmes";
    final String expectedDescription = "A categoria mais assistida";

    final boolean expectedIsAtiveTrue = true;

    @Test
    public void givenAValidGenre_whenCallsCreateGenre_shouldPersistGenre() {

        final var filmes =
                categoryMySqlGateway.create(Category.newCategory("Filmes", null, true));

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId());


        final var aGenre = Genre.newGenre("Ação", true);
        aGenre.addCategory(filmes.getId());
        final var expectedId = aGenre.getId();

        Assertions.assertEquals(0, genreRepository.count());

        final var actualGenre = genreGateway.create(aGenre);
        Assertions.assertEquals(1, genreRepository.count());

        //assert do gateway
        Assertions.assertEquals(aGenre.getName(), actualGenre.getName());
        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.getDeletedAt());
        Assertions.assertEquals(aGenre.getCategories(), actualGenre.getCategories());
        Assertions.assertEquals(aGenre.getCategories().size(), actualGenre.getCategories().size());
        Assertions.assertNull(actualGenre.getDeletedAt());

        final var persistedGenre = genreRepository.findById(expectedId.getValue()).get();

        //assert do que foi de fato persistido
        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedIsActive, persistedGenre.isActive());
        Assertions.assertEquals(expectedCategories, persistedGenre.getCategoryIDs());
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), persistedGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenre.getDeletedAt());
        Assertions.assertNull(persistedGenre.getDeletedAt());

    }

    @Test
    public void givenAValidGenreWithoutCategories_whenCallsCreateGenre_shouldPersistGenre() {


        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();


        final var aGenre = Genre.newGenre("Ação", true);
//        aGenre.addCategories(expectedCategories);

        final var expectedId = aGenre.getId();

        Assertions.assertEquals(0, genreRepository.count());

        final var actualGenre = genreGateway.create(aGenre);
        Assertions.assertEquals(1, genreRepository.count());

        //assert do gateway
        Assertions.assertEquals(aGenre.getName(), actualGenre.getName());
        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.getDeletedAt());
        Assertions.assertEquals(aGenre.getCategories(), actualGenre.getCategories());
        Assertions.assertEquals(aGenre.getCategories().size(), actualGenre.getCategories().size());
        Assertions.assertNull(actualGenre.getDeletedAt());

        final var persistedGenre = genreRepository.findById(expectedId.getValue()).get();

        //assert do que foi de fato persistido
        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedIsActive, persistedGenre.isActive());
        Assertions.assertEquals(expectedCategories, persistedGenre.getCategoryIDs());
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), persistedGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenre.getDeletedAt());
        Assertions.assertNull(persistedGenre.getDeletedAt());


    }


    @Test
    public void givenAValidGenreWithoutCategories_whenCallsUpdateGenreWithCategories_shouldPersistGenre() {
        //given

        final var filmes =
                categoryMySqlGateway.create(Category.newCategory("Filmes", null, true));

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId());


        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        Assertions.assertEquals(0, genreRepository.count());
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final var actualGenre = genreGateway.create(aGenre);
        Assertions.assertEquals(1, genreRepository.count());

        //assert do gateway
        Assertions.assertEquals(aGenre.getName(), actualGenre.getName());
        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.getDeletedAt());
        Assertions.assertEquals(aGenre.getCategories(), actualGenre.getCategories());
        Assertions.assertEquals(aGenre.getCategories().size(), actualGenre.getCategories().size());
        Assertions.assertNull(actualGenre.getDeletedAt());


        final var persistedGenre = genreRepository.findById(expectedId.getValue()).get();

        //assert do que foi de fato persistido
        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedIsActive, persistedGenre.isActive());
//        Assertions.assertEquals(expectedCategories, persistedGenre.getCategoryIDs());
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), persistedGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenre.getDeletedAt());
        Assertions.assertNull(persistedGenre.getDeletedAt());


        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // when
        final var persistedGenreUpdated = genreGateway.update(
                Genre.with(aGenre)
                        .update(expectedName, expectedIsActive, expectedCategories)
        );

        Assertions.assertEquals(1, genreRepository.count());


        Assertions.assertEquals(expectedName, persistedGenreUpdated.getName());
        Assertions.assertEquals(expectedIsActive, persistedGenreUpdated.isActive());
        Assertions.assertIterableEquals(sorted(expectedCategories), sorted(persistedGenre.getCategoryIDs()));
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenreUpdated.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(persistedGenreUpdated.getUpdatedAt()));
        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenreUpdated.getDeletedAt());
        Assertions.assertNull(persistedGenreUpdated.getDeletedAt());


    }

    @Test
    public void givenAValidGenreActive_whenCallsUpdateGenreInactivating_shouldPersistGenre() {
        //given

        final var filmes =
                categoryMySqlGateway.create(Category.newCategory("Filmes", null, true));

        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.of(filmes.getId());


        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        Assertions.assertEquals(0, genreRepository.count());

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // when
        final var persistedGenreUpdated = genreGateway.update(
                Genre.with(aGenre)
                        .update(expectedName, expectedIsActive, expectedCategories)
        );

        Assertions.assertEquals(1, genreRepository.count());


        Assertions.assertEquals(expectedName, persistedGenreUpdated.getName());
        Assertions.assertEquals(expectedId, persistedGenreUpdated.getId());
        Assertions.assertEquals(expectedIsActive, persistedGenreUpdated.isActive());
        Assertions.assertIterableEquals(sorted(expectedCategories), sorted(persistedGenreUpdated.getCategories()));
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenreUpdated.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(persistedGenreUpdated.getUpdatedAt()));
//        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenreUpdated.getDeletedAt());
        Assertions.assertNotNull(persistedGenreUpdated.getDeletedAt());
    }

    @Test
    public void givenAValidGenreWithCategories_whenCallsUpdateGenreCleaningCategories_shouldPersistGenre() {

        final var filmes =
                categoryMySqlGateway.create(Category.newCategory("Filmes", null, true));

        final var series =
                categoryMySqlGateway.create(Category.newCategory("Series", null, true));

        final var expectedName = "ac";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();


        final var aGenre = Genre.newGenre("ac", true);
        final var expectedId = aGenre.getId();

        aGenre.addCategories(List.of(filmes.getId(), series.getId()));

        Assertions.assertEquals(0, genreRepository.count());
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        genreRepository.saveAndFlush(GenreJpaEntity.from(aGenre));

        Assertions.assertEquals("ac", aGenre.getName());
        Assertions.assertEquals(2, aGenre.getCategories().size());


        final var actualGenre = genreGateway.update(
                Genre.with(aGenre).update(expectedName, expectedIsActive, expectedCategories));

        Assertions.assertEquals(1, genreRepository.count());

        Assertions.assertEquals(expectedId, actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.getDeletedAt());

        final var persistedGenre = genreRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedIsActive, persistedGenre.isActive());
        Assertions.assertEquals(expectedCategories, persistedGenre.getCategoryIDs());
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(persistedGenre.getUpdatedAt()));
        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenre.getDeletedAt());
        Assertions.assertNull(persistedGenre.getDeletedAt());
    }

    @Test
    public void givenTwoGenresAndOnePersisted_whenCallsExistsByIds_shouldReturnPersistedID() {
        // given
        final var aGenre = Genre.newGenre("Genre 1", true);

        final var expectedItems = 1;
        final var expectedId = aGenre.getId();

        Assertions.assertEquals(0, genreRepository.count());

        genreRepository.saveAndFlush(GenreJpaEntity.from(aGenre));

        // when
        final var actualGenre = genreGateway.existsByIds(List.of(GenreID.from("123"), expectedId));

        // then
        Assertions.assertEquals(expectedItems, actualGenre.size());
        Assertions.assertEquals(expectedId.getValue(), actualGenre.get(0).getValue());

    }

    @Test
    public void givenAValidGenreInactive_whenCallsUpdateGenreActivating_shouldPersistGenre() {

        final var expectedName = "ac";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aGenre = Genre.newGenre("ac", true);
        final var expectedId = aGenre.getId();

        Assertions.assertEquals(0, genreRepository.count());
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        genreRepository.saveAndFlush(GenreJpaEntity.from(aGenre));

        Assertions.assertEquals("ac", aGenre.getName());
        Assertions.assertEquals(0, aGenre.getCategories().size());


        final var actualGenre = genreGateway.update(
                Genre.with(aGenre).update(expectedName, expectedIsActive, expectedCategories));

        Assertions.assertEquals(1, genreRepository.count());

        Assertions.assertEquals(expectedId, actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.getDeletedAt());

        final var persistedGenre = genreRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, persistedGenre.getName());
        Assertions.assertEquals(expectedIsActive, persistedGenre.isActive());
        Assertions.assertEquals(expectedCategories, persistedGenre.getCategoryIDs());
        Assertions.assertEquals(aGenre.getCreatedAt(), persistedGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(persistedGenre.getUpdatedAt()));
        Assertions.assertEquals(aGenre.getDeletedAt(), persistedGenre.getDeletedAt());
        Assertions.assertNull(persistedGenre.getDeletedAt());
    }


    @Test
    public void givenAPrePersistedGenre_whenCallsDeleteById_shouldDeleteGenre() {
        // given
        final var aGenre = Genre.newGenre("Ação", true);

        genreRepository.saveAndFlush(GenreJpaEntity.from(aGenre));

        Assertions.assertEquals(1, genreRepository.count());

        // when
        genreGateway.deleteById(aGenre.getId());

        // then
        Assertions.assertEquals(0, genreRepository.count());
    }

    @Test
    public void givenAnInvalidGenre_whenCallsDeleteById_shouldReturnOK() {
        // given
        Assertions.assertEquals(0, genreRepository.count());

        // when
        genreGateway.deleteById(GenreID.from("123"));

        // then
        Assertions.assertEquals(0, genreRepository.count());
    }

    @Test
    public void givenAPrePersistedGenre_whenCallsFindById_shouldReturnGenre() {

        final var aGenre = Genre.newGenre("Ação", true);

        Assertions.assertEquals(0, genreRepository.count());
        genreRepository.saveAndFlush(GenreJpaEntity.from(aGenre));

        Assertions.assertEquals(1, genreRepository.count());
        final var fndGenre = genreRepository.findById(aGenre.getId().getValue()).get();

        Assertions.assertEquals(aGenre.getName(), fndGenre.getName());
        Assertions.assertEquals(aGenre.isActive(), fndGenre.isActive());
        Assertions.assertEquals(aGenre.getCategories(), fndGenre.getCategoryIDs());
        Assertions.assertEquals(aGenre.getCreatedAt(), fndGenre.getCreatedAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), fndGenre.getUpdatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), fndGenre.getDeletedAt());
        Assertions.assertNull(fndGenre.getDeletedAt());


    }

    @Test
    public void givenAInvalidGenreId_whenCallsFindById_shouldReturnEmpty() {

        final var expectedId = GenreID.from("123");

        Assertions.assertEquals(0, genreRepository.count());

        final var actualGenre = genreGateway.findById(expectedId);

        // then
        Assertions.assertTrue(actualGenre.isEmpty());

    }

    @Test
    public void givenEmptyGenres_whenCallFindAll_shouldReturnEmptyList() {

        //given

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualPage = genreGateway.findAll(aQuery);
        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedTotal, actualPage.items().size());


    }
    @ParameterizedTest
    @CsvSource({
            "aç,0,10,1,1,Ação",
            "dr,0,10,1,1,Drama",
            "com,0,10,1,1,Comédia romântica",
            "cien,0,10,1,1,Ficção científica",
            "terr,0,10,1,1,Terror",
    })
    public void givenAValidTerm_whenCallsFindAll_shouldReturnFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedGenreName
    ) {
        // given
        mockGenres();
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = genreGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());
        Assertions.assertEquals(expectedGenreName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,5,5,Ação",
            "name,desc,0,10,5,5,Terror",
            "createdAt,asc,0,10,5,5,Comédia romântica",
            "createdAt,desc,0,10,5,5,Ficção científica",
    })
    public void givenAValidSortAndDirection_whenCallsFindAll_shouldReturnOrdered(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedGenreName
    ) {
        // given
        mockGenres();
        final var expectedTerms = "";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = genreGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());
        Assertions.assertEquals(expectedGenreName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,5,Ação;Comédia romântica",
            "1,2,2,5,Drama;Ficção científica",
            "2,2,1,5,Terror",
    })
    public void givenAValidPaging_whenCallsFindAll_shouldReturnPaged(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedGenres
    ) {
        // given
        mockGenres();
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // when
        final var actualPage = genreGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());

        int index = 0;
        for (final var expectedName : expectedGenres.split(";")) {
            final var actualName = actualPage.items().get(index).getName();
            Assertions.assertEquals(expectedName, actualName);
            index++;
        }
    }

    private void mockGenres() {
        genreRepository.saveAllAndFlush(List.of(
                GenreJpaEntity.from(Genre.newGenre("Comédia romântica", true)),
                GenreJpaEntity.from(Genre.newGenre("Ação", true)),
                GenreJpaEntity.from(Genre.newGenre("Drama", true)),
                GenreJpaEntity.from(Genre.newGenre("Terror", true)),
                GenreJpaEntity.from(Genre.newGenre("Ficção científica", true))
        ));
    }


    private List<CategoryID> sorted(final List<CategoryID> expectedCategories) {
        return expectedCategories.stream()
                .sorted(Comparator.comparing(CategoryID::getValue))
                .toList();
    }


}
