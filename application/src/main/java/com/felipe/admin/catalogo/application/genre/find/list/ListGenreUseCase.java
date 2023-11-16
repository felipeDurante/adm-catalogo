package com.felipe.admin.catalogo.application.genre.find.list;

import com.felipe.admin.catalogo.application.UseCase;
import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;


public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutPut>> {

}