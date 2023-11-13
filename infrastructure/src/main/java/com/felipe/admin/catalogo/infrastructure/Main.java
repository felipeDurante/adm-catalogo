package com.felipe.admin.catalogo.infrastructure;

import com.felipe.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }

//    @Bean
//    public ApplicationRunner runner(CategoryRepository categoryRepository) {
//        return args -> {
//            var all = categoryRepository.findAll();
//
//            var filmes = Category.newCategory("Filmes", "descricao", true);
//
//            categoryRepository.saveAndFlush(CategoryEntity.from(filmes));
//
//            categoryRepository.deleteAll();
//
//        };
//    }
}