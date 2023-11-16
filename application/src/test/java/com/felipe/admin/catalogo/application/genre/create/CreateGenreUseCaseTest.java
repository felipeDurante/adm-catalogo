package com.felipe.admin.catalogo.application.genre.create;

import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.NotificationException;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateGenreUseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

//    @BeforeEach
//    void cleanUp() { // limpa o contexto dos mocks, garantindo que um teste nao interfira em outro
//        Mockito.reset(categoryGateway);
//        Mockito.reset(genreGateway);
//    }

    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private GenreGateway genreGateway;


    @Test
    public void givenAValidCommand_whenCallsCreateGerent_shouldReturnGenreId() {
        // given
        final var expectName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGateway, Mockito.times(1)).create(Mockito.argThat(aGenre ->
                Objects.equals(expectName, aGenre.getName())
                        && Objects.equals(expectedIsActive, aGenre.isActive())
                        && Objects.equals(expectedCategories, aGenre.getCategories())
                        && Objects.nonNull(aGenre.getId())
                        && Objects.nonNull(aGenre.getCreatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.isNull(aGenre.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_shouldReturnGenreId() {

        final var expectName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456"));

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsByIds(Mockito.any())).thenReturn(expectedCategories); // quando chamar o método exists by id, ira retornar as categorias esperadas


        when(genreGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).existsByIds(expectedCategories); // verifica q ele foi chamado recebendo as categorias enviadas

        Mockito.verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(expectName, aGenre.getName())
                        && Objects.equals(expectedIsActive, aGenre.isActive())
                        && Objects.equals(expectedCategories, aGenre.getCategories())
                        && Objects.nonNull(aGenre.getId())
                        && Objects.nonNull(aGenre.getCreatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.isNull(aGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsCreateGenre_shouldReturnGenreId() {
        // given
        final var expectName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(expectName, aGenre.getName())
                        && Objects.equals(expectedIsActive, aGenre.isActive())
                        && Objects.equals(expectedCategories, aGenre.getCategories())
                        && Objects.nonNull(aGenre.getId())
                        && Objects.nonNull(aGenre.getCreatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.nonNull(aGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateCategory_shouldReturnDomainException() {
        //given
        final var expectName = "";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();
        final var expectedMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        // when
        final var actualException =
                Assertions.assertThrows(NotificationException.class, () -> {
                    useCase.execute(aCommand);
                });
        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(actualException.getErros().size(), expectedErrorCount);
        Assertions.assertEquals(actualException.getErros().get(0).message(), expectedMessage);

        Mockito.verify(categoryGateway, Mockito.times(0)).existsByIds(expectedCategories); // verifica q ele foi chamado recebendo as categorias enviadas
        Mockito.verify(genreGateway, Mockito.times(0)).create(any()); // verifica q ele foi chamado recebendo as categorias enviadas
    }

    @Test
    public void givenAValidCommand_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturDomainException() {
        // given

        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");
        final var expectName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series, documentarios);
        final var expectedMessage = "Some categories could not be found: 123, 789";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsByIds(Mockito.any()))
                .thenReturn(List.of(series)); // quando chamar o método exists by id, ira retornar as categorias esperadas

        // when
        final var actualException =
                Assertions.assertThrows(NotificationException.class, () -> {
                    useCase.execute(aCommand);
                });
        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(actualException.getErros().size(), expectedErrorCount);
        Assertions.assertEquals(actualException.getErros().get(0).message(), expectedMessage);

        Mockito.verify(categoryGateway, Mockito.times(1)).existsByIds(expectedCategories); // verifica q ele foi chamado recebendo as categorias enviadas
        Mockito.verify(genreGateway, Mockito.times(0)).create(any()); // verifica q ele foi chamado recebendo as categorias enviadas

    }

    @Test
    public void givenAInvalidName_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException() {
        // given
        final var filmes = CategoryID.from("456");
        final var series = CategoryID.from("123");
        final var documentarios = CategoryID.from("789");

        final var expectName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series, documentarios);

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be empty";
        final var expectedErrorCount = 2;

        when(categoryGateway.existsByIds(any())) // somente a serie ira ser encontrada
                .thenReturn(List.of(series));

        final var aCommand =
                CreateGenreCommand.with(expectName, expectedIsActive, asString(expectedCategories));

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErros().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErros().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErros().get(1).message());

        Mockito.verify(categoryGateway, times(1)).existsByIds(any());
        Mockito.verify(genreGateway, times(0)).create(any());
    }

    private List<String> asString(List<CategoryID> categoryIDS) {
        return categoryIDS
                .stream()
                .map(CategoryID::getValue)
                .collect(Collectors.toList());
    }
}
