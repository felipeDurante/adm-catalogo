package com.felipe.admin.catalogo.domain.genre;

import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;
import com.felipe.admin.catalogo.domain.validation.Validator;

public class GenreValidator extends Validator {

    private static final int NAME_MAX_LENGTH =  255;
    private static final int NAME_MINX_LENGTH =  1;
    private final Genre genre;

    protected GenreValidator(final Genre genre, final ValidationHandler handler) {
        super(handler);
        this.genre = genre;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.genre.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || NAME_MINX_LENGTH < 1) {
            this.validationHandler().append(new Error("'name' must be between 1 and 255 characters"));
        }
    }

}
