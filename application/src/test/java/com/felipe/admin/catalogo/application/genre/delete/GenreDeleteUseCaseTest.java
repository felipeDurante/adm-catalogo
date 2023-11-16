package com.felipe.admin.catalogo.application.genre.delete;

import com.felipe.admin.catalogo.application.UseCaseTest;
import com.felipe.admin.catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import com.felipe.admin.catalogo.application.genre.update.UpdateGenreCommand;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.NotificationException;
import com.felipe.admin.catalogo.domain.genre.Genre;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import com.felipe.admin.catalogo.domain.genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreDeleteUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }
    //    1. Teste do caminho feliz ok
    //    2. Teste passando uma propriedade invalida(name) ok
    //    3. Teste atualizando uma categoria inativa
    //    4. Teste simulando um erro generico vindo do gateway
    //    5. Teste atualizr categoria passando id inválido

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {

        // given
        final var aGenre = Genre.newGenre("Ação", true);

        final var expectedId = aGenre.getId();

        doNothing()
                .when(genreGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // when
        Mockito.verify(genreGateway, times(1)).deleteById(expectedId);


    }

//    @Test
//    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
//        // given
//        final var aGenre = Genre.newGenre("acao", true);
//
//
//        final var expectedName = "Ação";
//        final var expectedIsActive = true;
//        final var expectedId = aGenre.getId();
//        final var expectedCategories = List.of(
//                CategoryID.from("123"),
//                CategoryID.from("465"),
//                CategoryID.from("789")
//        );
//
//
//        var aCommand = UpdateGenreCommand.with(
//                expectedId.getValue(),
//                expectedName,
//                expectedIsActive,
//                asString(expectedCategories)
//        );
//
//        when(genreGateway.findById(any()))
//                .thenReturn(Optional.of(Genre.with(aGenre)));
//
//        when(categoryGateway
//                .existsByIds(any()))
//                .thenReturn(expectedCategories);
//
//        when(genreGateway.update(any()))
//                .thenAnswer(returnsFirstArg());
//
//        // when
//        final var actualOutput = useCase.execute(aCommand);
//
//        // then
//        Assertions.assertNotNull(actualOutput);
//        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
//
//        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
//
//        Mockito.verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));
//
//        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdatedGenre ->
//                Objects.equals(expectedId, aUpdatedGenre.getId())
//                        && Objects.equals(expectedName, aUpdatedGenre.getName())
//                        && Objects.equals(expectedIsActive, aUpdatedGenre.isActive())
//                        && Objects.equals(expectedCategories, aUpdatedGenre.getCategories())
//                        && Objects.equals(aGenre.getCreatedAt(), aUpdatedGenre.getCreatedAt())
//                        && aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt())
//                        && Objects.isNull(aUpdatedGenre.getDeletedAt())
//        ));
//    }
//
//    @Test
//    public void givenAInvalidName_whenCallsUpdateGenre_shouldReturnDomainException() {
//        // given
//        final var aGenre = Genre.newGenre("acao", true);
//
//        final var expectedErrorMessage = "'name' should not be null";
//        final var expectedErrorCount = 1;
//
//        final String expectedName = null;
//        final var expectedIsActive = true;
//        final var expectedId = aGenre.getId();
//        final var expectedCategories = List.<CategoryID>of();
//
//
//        var aCommand = UpdateGenreCommand.with(
//                expectedId.getValue(),
//                expectedName,
//                expectedIsActive,
//                asString(expectedCategories)
//        );
//
//        when(genreGateway.findById(any()))
//                .thenReturn(Optional.of(Genre.with(aGenre)));
////
//        //when
//        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
////
//        Assertions.assertEquals(expectedErrorCount, actualException.getErros().size());
//        Assertions.assertEquals(expectedErrorMessage, actualException.getErros().get(0).message());
//
//        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
//
//        Mockito.verify(categoryGateway, times(0)).existsByIds(any());
//
//        Mockito.verify(genreGateway, times(0)).update(any());
//
//    }
//
//    @Test
//    public void givenAnInvalidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_shouldReturnNotificationException() {
//// given
//        final var filmes = CategoryID.from("123");
//        final var series = CategoryID.from("456");
//        final var documentarios = CategoryID.from("789");
//        final var aGenre = Genre.newGenre("acao", true);
//
//        final var expectedErrorMessage = "Some categories could not be found: 456, 789";
//        final var expectedErrorMessageTwo = "'name' should not be null";
//        final var expectedErrorCount = 2;
//
//        final String expectedName = null;
//        final var expectedIsActive = true;
//        final var expectedId = aGenre.getId();
//        final var expectedCategories = List.of(CategoryID.from("123"), CategoryID.from("456"), CategoryID.from("789"));
//
//
//        var aCommand = UpdateGenreCommand.with(
//                expectedId.getValue(),
//                expectedName,
//                expectedIsActive,
//                asString(expectedCategories)
//        );
//
//        when(genreGateway.findById(any()))
//                .thenReturn(Optional.of(Genre.with(aGenre)));
//
//        when(categoryGateway.existsByIds(any()))
//                .thenReturn(List.of(filmes));
////
//        //when
//        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
////
//        Assertions.assertEquals(expectedErrorCount, actualException.getErros().size());
//
//        Assertions.assertEquals(expectedErrorMessage, actualException.getErros().get(0).message());
//        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErros().get(1).message());
//
//        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
//
//        Mockito.verify(categoryGateway, times(1)).existsByIds(any());
//
//        Mockito.verify(genreGateway, times(0)).update(any());
//
//
//    }
//
//    //
////    @Test
////    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shouldReturnInactivateCategoryId() {
////
////        final var aCategory = Category.newCategory("Film", null, true);
////        final var expectedId = aCategory.getId();
////        final String expectedName = "Filmes";
////        final var expectedDescription = "A categoria mais assistida";
////        final var expectedIsAtive = false;
////
////
////        final var aCommand = UpdateCategoryCommand.with(
////                expectedId.getValue(),
////                expectedName,
////                expectedDescription,
////                expectedIsAtive);
////
////        when(categoryGateway
////                .findById(Mockito.eq(expectedId)))
////                .thenReturn(Optional.of(aCategory.clone()));
////
////        when(categoryGateway
////                .update(Mockito.any()))
////                .thenAnswer(returnsFirstArg()); // quando chamar o update retorna o que vier na entrada
////
////        Assertions.assertTrue(aCategory.isActive());
////        Assertions.assertNull(aCategory.getDeletedAt());
////
////        final var actualOutPut = useCase.execute(aCommand).get();
////        Assertions.assertNotNull(actualOutPut);
////        Assertions.assertNotNull(actualOutPut.id());
////
////        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
////
////        Mockito.verify(categoryGateway, times(1)).update(argThat(
////                aUpdateCategory ->
////                        Objects.equals(expectedName, aUpdateCategory.getName())
////                                && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
////                                && Objects.equals(expectedIsAtive, aUpdateCategory.isActive())
////                                && Objects.equals(expectedId, aUpdateCategory.getId())
//////                                && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt())
////                                && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
////                                && Objects.nonNull(aUpdateCategory.getDeletedAt())
////        ));
////
//////
////
////    }
////
//    @Test
//    public void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
//
//        final var aGenre = Genre.newGenre("acao", false);
//
//
//        final var expectedName = "Ação";
//        final var expectedIsActive = false;
//        final var expectedId = aGenre.getId();
//        final var expectedCategories = List.of(
//                CategoryID.from("123"),
//                CategoryID.from("465"),
//                CategoryID.from("789")
//        );
//
//
//        var aCommand = UpdateGenreCommand.with(
//                expectedId.getValue(),
//                expectedName,
//                expectedIsActive,
//                asString(expectedCategories)
//        );
//
//        when(genreGateway.findById(any()))
//                .thenReturn(Optional.of(Genre.with(aGenre)));
//
//        when(categoryGateway
//                .existsByIds(any()))
//                .thenReturn(expectedCategories);
//
//        when(genreGateway.update(any()))
//                .thenAnswer(returnsFirstArg());
//
//        // when
//        final var actualOutput = useCase.execute(aCommand);
//
//        // then
//        Assertions.assertNotNull(actualOutput);
//        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
//
//        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
//
//        Mockito.verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));
//
//        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdatedGenre ->
//                Objects.equals(expectedId, aUpdatedGenre.getId())
//                        && Objects.equals(expectedName, aUpdatedGenre.getName())
//                        && Objects.equals(expectedIsActive, aUpdatedGenre.isActive())
//                        && Objects.equals(expectedCategories, aUpdatedGenre.getCategories())
//                        && Objects.equals(aGenre.getCreatedAt(), aUpdatedGenre.getCreatedAt())
//                        && aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt())
//                        && Objects.nonNull(aUpdatedGenre.getDeletedAt())
//        ));
//
//
//    }
}

