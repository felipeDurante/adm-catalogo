package com.felipe.admin.catalogo.application.genre.find.get;

import com.felipe.admin.catalogo.application.UseCaseTest;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.NotFoundException;
import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;


public class GetGenreByIdUseCaseTest extends UseCaseTest {


    @InjectMocks
    private DefaultGetGenreByIdUseCase useCase;
    @Mock
    private GenreGateway genreGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetGenre_shouldReturnGenre() {

        //given

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aGenre = Genre.newGenre(expectedName, expectedIsActive)
                .addCategories(expectedCategories);

        final var expectedID = aGenre.getId();

        //when
        Mockito.when(genreGateway.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(aGenre));


        final var actualGenre = useCase.execute(expectedID.getValue());

        //then

        Assertions.assertEquals(actualGenre.id(), expectedID.getValue());
        Assertions.assertEquals(actualGenre.createdAt(), aGenre.getCreatedAt());
        Assertions.assertEquals(actualGenre.name(), aGenre.getName());
        Assertions.assertEquals(actualGenre.isActive(), aGenre.isActive());
        Assertions.assertEquals(actualGenre.deletedAt(), aGenre.getDeletedAt());
        Assertions.assertEquals(actualGenre.updatedAt(), aGenre.getUpdatedAt());
        Assertions.assertEquals(actualGenre.categories(), asString(aGenre.getCategories()));

        Mockito.verify(genreGateway, Mockito.times(1)).findById(ArgumentMatchers.eq(expectedID));


    }

    @Test
    public void givenAValidId_whenCallsGetGenreAndDoesNotExists_shouldReturnNotFound() {
        // given

        final var expectedId = GenreID.from("123");

        final var expectedMessage = "Genre with ID 123 was not found";
        //when
        final var output = Assertions.assertThrows(NotFoundException.class,
                () -> useCase.execute(expectedId.getValue()));

        //then


        Assertions.assertEquals(output.getMessage(), expectedMessage);






    }


}
