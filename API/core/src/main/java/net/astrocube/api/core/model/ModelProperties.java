package net.astrocube.api.core.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains all annotations related to models
 * {@link RouteKey} has to be defined while
 * {@link Cache} is optional
 */
public interface ModelProperties {

    /**
     * Defines the route associated with this model in the backend
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface RouteKey {
        String value();
    }

    /**
     * Using this would allow the model to be cached, if 0
     * model will not be cached
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Cache {
        int value() default 120;
    }

}