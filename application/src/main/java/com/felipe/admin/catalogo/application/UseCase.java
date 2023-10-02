package com.felipe.admin.catalogo.application;
import com.felipe.admin.catalogo.domain.category.Category;

public abstract class UseCase<IN,OUT> {

    public abstract OUT execute(IN anIn);





}