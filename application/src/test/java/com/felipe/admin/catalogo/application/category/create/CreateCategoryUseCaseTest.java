package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

//    1. Teste do caminho feliz
//    2. Teste passando uma propriedade invalida(name)
//    3. Teste criando uma categoria inativa
//    4. Teste simulando um erro generico vindo do gateway

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsAtive);

        when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutPut = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutPut);
        Assertions.assertNotNull(actualOutPut.id());

        verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedIsAtive, aCategory.isActive())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));

    }

    @Test
    public void givenAInValidName__whenCallsCreateCategory_shouldReturnDomainException() {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;
        final var expedtedMessageError = "'name' should not be null";
        final var errorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsAtive);

         final var notification = useCase.execute(aCommand).getLeft();

//        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));


        Assertions.assertEquals(errorCount, notification.getErros().size());
        Assertions.assertEquals(expedtedMessageError, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).create(any());

    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInaticvateCategoryId() {

        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsAtive);

        when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutPut = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutPut);
        Assertions.assertNotNull(actualOutPut.id());

        verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedIsAtive, aCategory.isActive())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.nonNull(aCategory.getDeletedAt())
        ));

    }

    @Test
    public void givenAValidCommand_whenCGatewayThrowsRandomException_shouldReturnException() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;
        final var expectedMessageError = "Gateway error";
        short errorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsAtive);

        when(categoryGateway.create(Mockito.any()))
                .thenThrow(new IllegalStateException("Gateway error"));

//        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

        final var notification = useCase.execute(aCommand).getLeft();

//        Assertions.assertEquals(expectedMessageError, actualException.getMessage());

        Assertions.assertEquals(errorCount, notification.getErros().size());
        Assertions.assertEquals(expectedMessageError, notification.firstError().message());

        verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedIsAtive, aCategory.isActive())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));
    }

}
