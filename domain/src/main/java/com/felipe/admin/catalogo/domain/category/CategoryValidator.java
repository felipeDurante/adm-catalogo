package com.felipe.admin.catalogo.domain.category;

import com.felipe.admin.catalogo.domain.validation.Error;
import com.felipe.admin.catalogo.domain.validation.ValidationHandler;
import com.felipe.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private static final int NAME_MAX_LENGTH =  255;
    private static final int NAME_MINX_LENGTH =  3;
    private final Category category;

    protected CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {

        checkNameConstrainsts();

    }

    private void checkNameConstrainsts() {

        final var name = this.category.getName();
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;

        }

        final var length = name.trim().length();

        if(length > NAME_MAX_LENGTH || length < NAME_MINX_LENGTH) {
            this.validationHandler().append(new Error("'name' mas be 3 and 255 characters"));
        }



    }
}
