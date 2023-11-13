package com.felipe.admin.catalogo.infrastructure.configuration.json;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.concurrent.Callable;

public enum Json {
    INSTANCE;

    public static ObjectMapper getMCopyMpper() { // se precisar customizar o mapper, utliza a copia para não sobrescrever
        return INSTANCE.mapper.copy();
    }

    // usado para ofuscar as exceções lançadas por esses métodos do object mapper
    //writeValueAsString
    //readValue
    public static String writeValueAsString(final Object obj) {
        return invoke(() -> INSTANCE.mapper.writeValueAsString(obj));
    }

    public static <T> T readValue(final String json, final Class<T> clazz) {
        return invoke(() -> INSTANCE.mapper.readValue(json, clazz));
    }

    private static <T> T invoke(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .dateFormat(new StdDateFormat())
            .featuresToDisable(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                    DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            )
            .modules(new JavaTimeModule()// faz com que o object mapper utilize a blibliotca do java time para serializacao e deseraliacao de datas
                    , new Jdk8Module()   // utliza o o jd8 para deseralizar e serealizar com o options do java
                    , afterBurnerModule())
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .build();

    private AfterburnerModule afterBurnerModule() {

        // faz com  que o seja gerado apenas bytecode para getters,setter publicos e campos(propriedades) sem ele para o jackson
        var module = new AfterburnerModule();
        module.setUseValueClassLoader(false);
        return module;
    }
}
