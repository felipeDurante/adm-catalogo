package com.felipe.admin.catalogo;

import com.felipe.admin.catalogo.infrastructure.configuration.ObjectMapperConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@WebMvcTest //test de controladores, deserializacao e serializacao e etc
@Import(ObjectMapperConfig.class)
public @interface ControllerTest {

//    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers") // faz um bind para poder usar a anotação @ControllerTest
//    Class<?>[] controllers() default {};

//    @AliasFor(annotation = WebMvcTest.class , attribute = "controllers")
//    Class<?>[] controllers() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};


//    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
//    Class<?>[] controllers() default {};

}
