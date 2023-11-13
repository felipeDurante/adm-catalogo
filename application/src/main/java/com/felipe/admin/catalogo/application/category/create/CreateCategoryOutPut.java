package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.domain.category.Category;

public record CreateCategoryOutPut(
        String id
) {
    public static CreateCategoryOutPut from(final String aCategory) {
        return new CreateCategoryOutPut(aCategory);
    }

    public static CreateCategoryOutPut from(final Category anID) {
        return new CreateCategoryOutPut(anID.getId().getValue());
    }
}
