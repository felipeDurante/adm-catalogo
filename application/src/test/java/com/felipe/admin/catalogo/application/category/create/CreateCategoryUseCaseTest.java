package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateCategoryUseCaseTest {

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

        CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutPut = useCase.execute(aCommand);

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

}
