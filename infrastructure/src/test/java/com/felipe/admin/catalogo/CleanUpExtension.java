package com.felipe.admin.catalogo;

import com.felipe.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.felipe.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

public class CleanUpExtension implements BeforeEachCallback {

//    @Override
//    public void beforeEach(ExtensionContext context) throws Exception {
//        final var repositories = SpringExtension
//                .getApplicationContext(context)
//                .getBeansOfType(CrudRepository.class)
//                .values();
//        cleanUp(repositories);
//    }
//
//    private void cleanUp(final Collection<CrudRepository> repositories) {
//        repositories.forEach(CrudRepository::deleteAll);
//    }

    /**
     * A cada teste é recuperado os repositories executado o flush e clear dos entity manager
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeEach(final ExtensionContext context) {
        final var applicationContext = SpringExtension
                .getApplicationContext(context);

        cleanUp(List.of(
                applicationContext.getBean(GenreRepository.class),
                applicationContext.getBean(CategoryRepository.class)));
//        final var em = applicationContext.getBean(TestEntityManager.class);
//        em.flush();
//        em.clear();
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
