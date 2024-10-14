package nl.rdb.spring_boot_examples.schema.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSchema {

    String prefix() default "";

    Class<?> schemaClass() default void.class;
}