package com.felipe.admin.catalogo.domain.validation.handler;

import com.felipe.admin.catalogo.domain.exceptions.DomainException;
import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(ValidationHandler anError) {
        throw DomainException.with(anError.getErros());
    }

    @Override
    public <T> T validate(final Validation<T> anError) {
        try {
            return anError.validate();
        } catch (final Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErros() {
        return null;
    }
}
