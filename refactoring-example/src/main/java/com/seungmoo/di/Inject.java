package com.seungmoo.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 기능을 수행하는 애노테이션은 웬만하면 RUNTIME으로 셋팅해줘야 한다.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
}
