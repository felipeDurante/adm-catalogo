package com.felipe.admin.catalogo.domain.exceptions;

import com.felipe.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException {

    private final List<Error> erros;

    private DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.erros = anErrors;
    }

    public static DomainException with(Error anError) {
        return new DomainException("", List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErros() {
        return erros;
    }
}
