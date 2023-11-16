package com.felipe.admin.catalogo.domain.genre;

import com.felipe.admin.catalogo.domain.pagination.Pagination;
import com.felipe.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Optional;

/**
 * @implSpec {@summary
 * Classe responsavel por fazer o papel de comunicacao GenreGateway com as demais  camadas
 * }
 * Interface que define as operações relacionadas a gêneros.
 */
public interface GenreGateway {

    /**
     * Cria um novo gênero.
     *
     * @param genre O objeto Genre a ser criado.
     * @return O gênero criado.
     */
    Genre create(Genre genre);

    /**
     * Exclui um gênero com base no ID do gênero.
     *
     * @param genreID O ID do gênero a ser excluído.
     */
    void deleteById(GenreID genreID);

    /**
     * Encontra um gênero com base no ID do gênero.
     *
     * @param genreID O ID do gênero a ser encontrado.
     * @return Um Optional contendo o gênero, se encontrado.
     */
    Optional<Genre> findById(GenreID genreID);

    /**
     * Atualiza as informações de um gênero.
     *
     * @param genre O objeto Genre com as informações atualizadas.
     * @return O gênero atualizado.
     */
    Genre update(Genre genre);

    /**
     * Recupera uma lista paginada de todos os gêneros.
     *
     * @return Uma instância de Pagination contendo a lista paginada de gêneros.
     */
    Pagination<Genre> findAll(SearchQuery aQuery);
}
