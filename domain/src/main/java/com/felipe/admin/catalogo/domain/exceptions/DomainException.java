package com.felipe.admin.catalogo.domain.exceptions;

import com.felipe.admin.catalogo.domain.validation.Error;

import java.util.List;


public class DomainException extends NoStackTraceException {

    /* Classe responsavel por lancar a excecao pegando apenas a primeira mensagem de erro  */
    protected final List<Error> erros;

    DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.erros = anErrors;
    }

    public static DomainException with(Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErros() {
        return erros;
    }
}
