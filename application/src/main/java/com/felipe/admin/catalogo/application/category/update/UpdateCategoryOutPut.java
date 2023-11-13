package com.felipe.admin.catalogo.application.category.update;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryID;

public record UpdateCategoryOutPut  (
        String id
){
    public static UpdateCategoryOutPut from(final Category aCategory) {
        return new UpdateCategoryOutPut(aCategory.getId().getValue());
    }

    public static UpdateCategoryOutPut from(final String anID) {
        return new UpdateCategoryOutPut(anID);
    }
}
