package com.seungmoo;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FieldAnnotation {
    String nameDefault() default "seungmoo";
    String value();
}
