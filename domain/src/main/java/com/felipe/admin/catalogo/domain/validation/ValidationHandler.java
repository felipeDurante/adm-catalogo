package com.felipe.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler anError);
    ValidationHandler validate(Validation anError);

    public List<Error> getErros();


    public interface Validation {

        void validate();
    }

    default boolean hasError() {
         return getErros()!=null &&  !getErros().isEmpty();
    }


}
