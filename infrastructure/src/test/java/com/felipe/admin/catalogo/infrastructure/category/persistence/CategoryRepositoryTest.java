package com.felipe.admin.catalogo.infrastructure.category.persistence;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.infrastructure.category.MySqlGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySqlGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;


    final String expectedName = "Filmes";
    final String expectedDescription = "A categoria mais assistida";

    @Test
    public void givenAnInvalidNullName_whenCallSave_shouldRetournError() {
        final var expectedPropertyName = "name";
//        final var expectedMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity.createdAt";
        final Category aCategory = Category.newCategory(expectedName, expectedDescription, true);

        final CategoryEntity anEntity = CategoryEntity.from(aCategory);
        anEntity.setName(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals("not-null property references a null or transient value : com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryEntity.name", actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {
        final var expectedPropertyName = "createdAt";
        final var expectedMessage = "not-null property references a null or transient value : com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryEntity.createdAt";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryEntity.from(aCategory);
        anEntity.setCreatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidNullUpdatedAt_whenCallsSave_shouldReturnError() {
        final var expectedPropertyName = "updatedAt";
        final var expectedMessage = "not-null property references a null or transient value : com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryEntity.updatedAt";

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final var anEntity = CategoryEntity.from(aCategory);
        anEntity.setUpdatedAt(null);

        final var actualException =
                Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(anEntity));

        final var actualCause =
                Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());

        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

}
