package com.felipe.admin.catalogo.infrastructure.configuration.usecases;

import com.felipe.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.felipe.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.felipe.admin.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.felipe.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.get.DefaultGetCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.get.GetCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.list.DefaultListCategoriesUseCase;
import com.felipe.admin.catalogo.application.category.find.list.ListCategoriesUseCase;
import com.felipe.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}
