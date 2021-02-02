package com.seungmoo;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface AnotherAnnotation  {
    String nameDefault() default "seungmoo";
    String value();
}
