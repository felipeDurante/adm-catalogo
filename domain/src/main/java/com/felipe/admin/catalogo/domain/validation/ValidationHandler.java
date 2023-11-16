package com.felipe.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);
//    ValidationHandler validate(Validation anError);

    <T> T validate(Validation<T> aValidation);

    List<Error> getErros();

    default boolean hasError() {
        return getErros() != null && !getErros().isEmpty();
    }

    default Error firstError() {
        return (getErros() != null && !getErros().isEmpty()) ? getErros().get(0) : null;
    }

    interface Validation<T> { //retorna valor generico, permitindo a generealizacao
        T validate();
    }


}
