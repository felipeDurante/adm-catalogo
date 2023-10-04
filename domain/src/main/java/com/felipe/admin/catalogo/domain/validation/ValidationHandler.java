package com.felipe.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler anError);
    ValidationHandler validate(Validation anError);

    public List<Error> getErros();




    interface Validation {
        void validate();
    }

    default Error firstError() {
        return (getErros() != null && !getErros().isEmpty()) ? getErros().get(0) : null;
    }

    default boolean hasError() {
         return getErros()!=null &&  !getErros().isEmpty();
    }


}
