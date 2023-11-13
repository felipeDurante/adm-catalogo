package com.felipe.admin.catalogo.application.category.delete;

import com.felipe.admin.catalogo.IntegrationTest;
import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryEntity;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@IntegrationTest
public class DeleteCategoryUseCaseIT {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        save(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, categoryRepository.count());


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

    private void save(final Category... aCategory) {
        categoryRepository.saveAllAndFlush(
                Arrays.stream(aCategory)
                        .map(CategoryEntity::from)
                        .toList()
        );
    }
}
