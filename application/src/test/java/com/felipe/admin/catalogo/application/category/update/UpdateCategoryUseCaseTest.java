package com.felipe.admin.catalogo.application.category.update;

import com.felipe.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    //    1. Teste do caminho feliz ok
    //    2. Teste passando uma propriedade invalida(name) ok
    //    3. Teste atualizando uma categoria inativa
    //    4. Teste simulando um erro generico vindo do gateway
    //    5. Teste atualizr categoria passando id inválido

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;


        final var aCategory = Category.newCategory("Film ", null, true);

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsAtive);


        when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        when(categoryGateway.update(Mockito.any())).thenAnswer(returnsFirstArg()); // quando chamar o update retorna o que vier na entrada

        final var actualOutPut = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutPut);
        Assertions.assertNotNull(actualOutPut.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(
                aUpdateCategory ->
                        Objects.equals(expectedName, aUpdateCategory.getName())
                                && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
                                && Objects.equals(expectedIsAtive, aUpdateCategory.isActive())
                                && Objects.equals(expectedId, aUpdateCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                                && Objects.isNull(aUpdateCategory.getDeletedAt())
        ));

    }

    @Test
    public void givenAInValidName_whenCallsUpdateCategory_shouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film ", null, true);
        final var expectedId = aCategory.getId();
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;


        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsAtive);

        when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft(); // pega o erro do comando

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErros().size());

        Mockito.verify(categoryGateway, times(0)).update(any());


    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shouldReturnInactivateCategoryId() {

        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedId = aCategory.getId();
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;


        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsAtive);

        when(categoryGateway
                .findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway
                .update(Mockito.any()))
                .thenAnswer(returnsFirstArg()); // quando chamar o update retorna o que vier na entrada

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutPut = useCase.execute(aCommand).get();
        Assertions.assertNotNull(actualOutPut);
        Assertions.assertNotNull(actualOutPut.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdateCategory ->
                        Objects.equals(expectedName, aUpdateCategory.getName())
                                && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
                                && Objects.equals(expectedIsAtive, aUpdateCategory.isActive())
                                && Objects.equals(expectedId, aUpdateCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                                && Objects.nonNull(aUpdateCategory.getDeletedAt())
        ));

//

    }

    @Test
    public void givenAValidInactiveCommandCategory_whenCallsCreateCategory_shouldReturnUpdateCategoryId() {

        final var aCategory = Category.newCategory("Film", null, true);

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());


        final var expectedId = aCategory.getId();
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = false;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedActive);

        when(categoryGateway
                .findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway
                .update(Mockito.any()))
                .thenAnswer(returnsFirstArg()); // quando chamar o update retorna o que vier na entrada

        final var actualOutPut = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutPut);
        Assertions.assertNotNull(actualOutPut.id());

        verify(categoryGateway, Mockito.times(1)).update(Mockito.argThat(aUpdate ->
                Objects.equals(expectedName, aUpdate.getName())
                        && Objects.equals(expectedActive, aUpdate.isActive())
                        && Objects.equals(expectedDescription, aUpdate.getDescription())
                        && Objects.nonNull(aUpdate.getId())
                        && Objects.nonNull(aUpdate.getCreatedAt())
                        && Objects.nonNull(aUpdate.getUpdatedAt())
                        && Objects.nonNull(aUpdate.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommand_whenCGatewayThrowsRandomException_shouldReturnException() {

        final var aCategory = Category.newCategory("Film", null, true);

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());


        final var expectedId = aCategory.getId();
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);


        when(categoryGateway
                .findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(Mockito.any()))
                .thenThrow(new IllegalStateException("Gateway error"));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErros().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(categoryGateway, Mockito.times(1)).
                update(Mockito.argThat(aUpdate ->
                        Objects.equals(expectedName, aUpdate.getName())
                                && Objects.equals(expectedIsActive, aUpdate.isActive())
                                && Objects.equals(expectedDescription, aUpdate.getDescription())
                                && Objects.nonNull(aUpdate.getId())
                                && Objects.nonNull(aUpdate.getCreatedAt())
                                && Objects.nonNull(aUpdate.getUpdatedAt())
                                && Objects.isNull(aUpdate.getDeletedAt())
                ));
    }

    @Test
    public void givenCommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {

        final var expectedId = "123";
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;
        final var expectedMessageError = "Category with ID 123 was not found";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsAtive);

        when(categoryGateway
                .findById(Mockito.eq(CategoryID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException = Assertions
                .assertThrows(DomainException.class, () -> useCase.execute(aCommand));


        Assertions.assertEquals(1, actualException.getErros().size());
        Assertions.assertEquals(expectedMessageError, actualException.getMessage());

        Mockito.verify(categoryGateway, times(1))
                .findById(eq(CategoryID.from(expectedId)));

        Mockito.verify(categoryGateway, times(0))
                .update(any()); // o any é pq o interesse desse teste é verificar a quantidade de vezes e não a informação em si que está sendo passada

    }


}
