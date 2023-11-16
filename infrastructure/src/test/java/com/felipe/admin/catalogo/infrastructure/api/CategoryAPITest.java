package com.felipe.admin.catalogo.infrastructure.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipe.admin.catalogo.application.category.create.CreateCategoryOutPut;
import com.felipe.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.felipe.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.get.CategoryOutPut;
import com.felipe.admin.catalogo.application.category.find.get.GetCategoryUseCase;
import com.felipe.admin.catalogo.application.category.find.list.CategoryListOutput;
import com.felipe.admin.catalogo.application.category.find.list.ListCategoriesUseCase;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryCommand;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryOutPut;
import com.felipe.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryID;
import com.felipe.admin.catalogo.domain.exceptions.DomainException;
import com.felipe.admin.catalogo.domain.exceptions.NotFoundException;
import com.felipe.admin.catalogo.domain.validation.handler.Notification;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.ControllerTest;
import com.felipe.admin.catalogo.infrastructure.category.models.CreateCategoryRequest;
import com.felipe.admin.catalogo.infrastructure.category.models.UpdateCategoryRequest;
import io.vavr.API;
import io.vavr.control.Either;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;
    @MockBean
    private GetCategoryUseCase getCategoryUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @MockBean
    private ListCategoriesUseCase listCategoriesUseCase;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;

        final var anInput = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsAtive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Either.right(CreateCategoryOutPut.from("123")));

        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/categories/123"),
                        MockMvcResultMatchers.jsonPath("$.id", equalTo("123")));

        Mockito.verify(createCategoryUseCase, Mockito.times(1))
                .execute(
                        Mockito.argThat(cmd ->
                                Objects.equals(expectedName, cmd.name()) &&
                                        Objects.equals(expectedDescription, cmd.description()) &&
                                        Objects.equals(expectedIsAtive, cmd.isActive())
                        ));

    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;
        final var expectedMessage = "'name' should not be null";

        final var anInput = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsAtive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        header().string("Location", Matchers.nullValue()),
                        MockMvcResultMatchers.jsonPath("$.erros", Matchers.hasSize(1)),
                        MockMvcResultMatchers.jsonPath("$.erros[0].message", equalTo(expectedMessage))
                );

        Mockito.verify(createCategoryUseCase, Mockito.times(1))
                .execute(
                        Mockito.argThat(cmd ->
                                Objects.equals(expectedName, cmd.name()) &&
                                        Objects.equals(expectedDescription, cmd.description()) &&
                                        Objects.equals(expectedIsAtive, cmd.isActive())
                        ));

    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        //given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;
        final var expectedMessage = "'name' should not be null";

        final var anInput = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsAtive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        header().string("Location", Matchers.nullValue()),
                        MockMvcResultMatchers.jsonPath("$.message", equalTo(expectedMessage)),
                        MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)),
                        MockMvcResultMatchers.jsonPath("$.errors[0].message", equalTo(expectedMessage))
                );

        Mockito.verify(createCategoryUseCase, Mockito.times(1))
                .execute(
                        Mockito.argThat(cmd ->
                                Objects.equals(expectedName, cmd.name()) &&
                                        Objects.equals(expectedDescription, cmd.description()) &&
                                        Objects.equals(expectedIsAtive, cmd.isActive())
                        ));
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        //given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive); // cria uma entity

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryUseCase.execute(any()))
                .thenReturn(CategoryOutPut.from(aCategory));
//        when
        final var request = MockMvcRequestBuilders
                .get("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());
//        then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(expectedId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(expectedName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt())));

        Mockito.verify(getCategoryUseCase, Mockito.times(1)).execute(Mockito.eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        // given
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");

        when(getCategoryUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, expectedId));

        // when
        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId.getValue())
//                .with(ApiTest.CATEGORIES_JWT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // then
        response.andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

    @Test
    public void givenAValidIdCommand_whenCallsUpdateCategory_shouldReturnCategoryID() throws Exception {
        //given
        final var expectedName = "Filmes";
        final var expectedId = "123";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(API.Right(UpdateCategoryOutPut.from(expectedId)));

        var input = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);
//        when
        final var request = MockMvcRequestBuilders
                .put("/categories/{ids}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        final var response = this.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(expectedId)));

        Mockito.verify(updateCategoryUseCase, Mockito.times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));

//        Mockito.verify(getCategoryUseCase, Mockito.times(1)).execute(Mockito.eq(expectedId));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorCount = 1;
        final var expectedMessage = "'name' should not be null";


        when(updateCategoryUseCase.execute(any()))
                .thenReturn(API.Left(Notification.create(new Error(expectedMessage))));

        var input = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);
//        when
        final var request = MockMvcRequestBuilders
                .put("/categories/{ids}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        final var response = this.mvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print());

        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros", hasSize(expectedErrorCount)))
                .andExpect(jsonPath("$.erros[0].message", equalTo(expectedMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() throws Exception {
        // given
        final var expectedId = "not-found";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "Category with ID not-found was not found";

        when(updateCategoryUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, CategoryID.from(expectedId)));

        final var aCommand =
                new UpdateCategoryCommand(expectedId, expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = MockMvcRequestBuilders.put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldReturnNoContent() throws Exception {
        // given
        final var expectedId = "123";

        doNothing()
                .when(deleteCategoryUseCase).execute(any());

        // when
        final var request = MockMvcRequestBuilders.delete("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // then
        response.andExpect(status().isNoContent());

        verify(deleteCategoryUseCase, times(1)).execute(eq(expectedId));
    }
    @Test
    public void givenValidParams_whenCallsListCategories_shouldReturnCategories() throws Exception {
        // given
        final var aCategory = Category.newCategory("Movies", null, true);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "movies";
        final var expectedSort = "description";
        final var expectedDirection = "desc";
        final var expectedItemsCount = 1;
        final var expectedTotal = 1;

        final var expectedItems = List.of(CategoryListOutput.from(aCategory));

        when(listCategoriesUseCase.execute(any()))
            .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));

    // when
    final var request = MockMvcRequestBuilders.get("/categories")
            .queryParam("page", String.valueOf(expectedPage))
            .queryParam("perPage", String.valueOf(expectedPerPage))
            .queryParam("sort", expectedSort)
            .queryParam("dir", expectedDirection)
            .queryParam("search", expectedTerms)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

    final var response = this.mvc.perform(request)
            .andDo(MockMvcResultHandlers.print());

    // then
        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
            .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
            .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
            .andExpect(jsonPath("$.items", hasSize(expectedItemsCount)))
            .andExpect(jsonPath("$.items[0].id", equalTo(aCategory.getId().getValue())))
            .andExpect(jsonPath("$.items[0].name", equalTo(aCategory.getName())))
            .andExpect(jsonPath("$.items[0].description", equalTo(aCategory.getDescription())))
            .andExpect(jsonPath("$.items[0].is_active", equalTo(aCategory.isActive())))
            .andExpect(jsonPath("$.items[0].created_at", equalTo(aCategory.getCreatedAt().toString())))
            .andExpect(jsonPath("$.items[0].deleted_at", equalTo(aCategory.getDeletedAt())));

    verify(listCategoriesUseCase, times(1)).execute(argThat(query ->
            Objects.equals(expectedPage, query.page())
            && Objects.equals(expectedPerPage, query.perPage())
            && Objects.equals(expectedDirection, query.direction())
            && Objects.equals(expectedSort, query.sort())
            && Objects.equals(expectedTerms, query.terms())
            ));
}


}
