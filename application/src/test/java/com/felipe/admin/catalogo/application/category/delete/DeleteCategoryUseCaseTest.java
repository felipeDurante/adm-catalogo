package com.felipe.admin.catalogo.application.category.delete;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {
    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @BeforeEach
    void cleanUp() { // limpa o contexto dos mocks, garantindo que um teste nao interfira em outro
        Mockito.reset(categoryGateway);
    }

    @Mock
    private CategoryGateway categoryGateway;


    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expetecId = aCategory.getId();

        doNothing()
                .when(categoryGateway)
                .deleteById(eq(expetecId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expetecId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expetecId);


    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {
        final var expetecId = CategoryID.from("123");

        doNothing()
                .when(categoryGateway)
                .deleteById(eq(expetecId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expetecId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expetecId);
    }

    @Test
    public void givenAValidId_whenGatewayTrhwosException_shouldReturnException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expetecId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway)
                .deleteById(eq(expetecId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expetecId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(expetecId);
    }


}
