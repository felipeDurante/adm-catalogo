package com.felipe.admin.catalogo.infrastructure.api;

import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.infrastructure.category.models.CategoryApiResponse;
import com.felipe.admin.catalogo.infrastructure.category.models.CreateCategoryRequest;
import com.felipe.admin.catalogo.infrastructure.category.models.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "categories")
@Tag(name = "Categories")
public interface CategoryAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created sucessfully"),
            @ApiResponse(responseCode = "422", description = "Processable error"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest createCategoryInput);

    @GetMapping(value = "{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get a category by identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category retrieved sucessfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    CategoryApiResponse getCategory(@PathVariable(name = "id") String id);


    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated sucessfully"),
            @ApiResponse(responseCode = "422", description = "Processable error"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    ResponseEntity<?> updateCategoryById(@PathVariable(name = "id") String id, @RequestBody UpdateCategoryRequest updateCategoryApiInput);

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteById(@PathVariable(name = "id") String id);

    @GetMapping
    @Operation(summary = "List all categories paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed sucessfully"),
            @ApiResponse(responseCode = "422", description = "Invalid Parameter was recebe"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir);

}
