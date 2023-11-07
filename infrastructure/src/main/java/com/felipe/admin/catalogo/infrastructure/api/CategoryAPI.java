package com.felipe.admin.catalogo.infrastructure.api;

import com.felipe.admin.catalogo.domain.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    ResponseEntity<?> createCategory();

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
