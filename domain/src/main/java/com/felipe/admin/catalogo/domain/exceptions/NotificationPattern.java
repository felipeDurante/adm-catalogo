package com.felipe.admin.catalogo.domain.exceptions;

import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationPattern implements ValidationHandler {


    private final List<Error> errors;

    private NotificationPattern(final List<Error> errors) {
        this.errors = errors;
    }

    public static NotificationPattern create() {
        return new NotificationPattern(new ArrayList<>());
    }

    public static NotificationPattern create(final Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    public static NotificationPattern create(final Error anError) {
        return new NotificationPattern(new ArrayList<>()).append(anError);
    }

    @Override
    public NotificationPattern append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public NotificationPattern append(ValidationHandler anError) {
        this.errors.addAll(anError.getErros());
        return this;
    }

    @Override
    public NotificationPattern validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErros());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErros() {
        return this.errors;
    }


}
