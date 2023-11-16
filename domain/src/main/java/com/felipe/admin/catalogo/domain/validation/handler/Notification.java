package com.felipe.admin.catalogo.domain.validation.handler;

import com.felipe.admin.catalogo.domain.exceptions.DomainException;
import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {


    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anError) {
        this.errors.addAll(anError.getErros());
        return this;
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErros());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }
//    @Override
//    public <T> validate(final Validation<T> aValidation) {
//        try {
//            aValidation.validate();
//        } catch (final DomainException ex) {
//            this.errors.addAll(ex.getErros());
//        } catch (final Throwable t) {
//            this.errors.add(new Error(t.getMessage()));
//        }
//        return null;
//    }

    @Override
    public List<Error> getErros() {
        return this.errors;
    }


}
