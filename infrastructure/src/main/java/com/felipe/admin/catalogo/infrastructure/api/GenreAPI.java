package com.felipe.admin.catalogo.infrastructure.api;

import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.felipe.admin.catalogo.infrastructure.genre.models.GenreApiResponse;
import com.felipe.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "genres")
@Tag(name = "Genre")
public interface GenreAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> create(@RequestBody CreateGenreRequest input);

    @GetMapping(value = "{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get a Genre by identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre retrieved sucessfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    GenreApiResponse getGenre(@PathVariable(name = "id") String id);


    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated sucessfully"),
            @ApiResponse(responseCode = "422", description = "Processable error"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    ResponseEntity<?> updateGenreById(@PathVariable(name = "id") String id, @RequestBody UpdateGenreRequest updateGenreRequest);

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Genre was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteGenreById(@PathVariable(name = "id") String id);

    @GetMapping
    @Operation(summary = "List all genres paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed sucessfully"),
            @ApiResponse(responseCode = "422", description = "Invalid Parameter was recebe"),
            @ApiResponse(responseCode = "500", description = "Internal sever error was throw")
    })
    Pagination<?> listGenres(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir);
}
