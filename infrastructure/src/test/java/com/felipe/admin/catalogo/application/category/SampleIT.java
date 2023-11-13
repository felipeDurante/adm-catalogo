package com.felipe.admin.catalogo.application.category;

import com.felipe.admin.catalogo.IntegrationTest;
import com.felipe.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CreateCategoryUseCase useCase;

    @Test
    public void test() {
        Assertions.assertNotNull(categoryRepository);
        Assertions.assertNotNull(useCase);
    }
}
