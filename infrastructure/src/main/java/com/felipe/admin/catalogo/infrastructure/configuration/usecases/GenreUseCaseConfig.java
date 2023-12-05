package com.felipe.admin.catalogo.infrastructure.configuration.usecases;

import com.felipe.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.felipe.admin.catalogo.application.genre.create.CreateGenreUseCase;
import com.felipe.admin.catalogo.application.genre.create.DefaultCreateGenreUseCase;
import com.felipe.admin.catalogo.application.genre.delete.DefaultDeleteGenreUseCase;
import com.felipe.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.felipe.admin.catalogo.application.genre.find.get.DefaultGetGenreByIdUseCase;
import com.felipe.admin.catalogo.application.genre.find.get.GetGenreByIdUseCase;
import com.felipe.admin.catalogo.application.genre.find.list.DefaultListGenreByIdUseCase;
import com.felipe.admin.catalogo.application.genre.find.list.ListGenreUseCase;
import com.felipe.admin.catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import com.felipe.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import com.felipe.admin.catalogo.domain.category.CategoryGateway;
import com.felipe.admin.catalogo.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {
    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public GenreUseCaseConfig(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(genreGateway, categoryGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenreUseCase listGenreUseCase() {
        return new DefaultListGenreByIdUseCase(genreGateway);
    }


}
