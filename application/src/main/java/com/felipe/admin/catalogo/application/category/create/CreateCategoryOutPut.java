package com.felipe.admin.catalogo.application.category.create;

import com.felipe.admin.catalogo.domain.category.Category;
import com.felipe.admin.catalogo.domain.category.CategoryID;

public record  CreateCategoryOutPut (
        CategoryID id
){
    public static CreateCategoryOutPut from(final Category aCategory) {
        return new CreateCategoryOutPut(aCategory.getId());
    }
}
