package com.felipe.admin.catalogo.application.category.update;

public record UpdateCategoryCommand(String id, String name, String description, boolean isActive) {

    public static UpdateCategoryCommand with(final String andID, final String aName, final String aDescription, final boolean isActive) {
        return new UpdateCategoryCommand(andID, aName, aDescription, isActive);
    }

}
