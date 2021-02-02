package com.seungmoo;

import java.lang.annotation.*;

/**
 * Annotation은 기본적으로 주석과 같은 취급을 받는다.
 * class 까지는 Annotation이 남는다.
 * BUT class(Byte Code)를 로딩했을 때, Memory에서는 해당 class에 Annotation이 남지 않는다.
 * --> RUNTIME에서도 이 Annotation을 유지하고 싶다 --> @Retention(RetentionPolicy.RUNTIME) 이렇게 선언 ㄱㄱ
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * TYPE과 FIELD에만 붙일 수 있다.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
/**
 * 상속이 되는 어노테이션 임을 선언
 */
@Inherited
public @interface MyAnnoation {

    // Annotation도 값을 가질 수 있다. name() 이렇게 선언해야댐
    // default가 없는 값의 경우, Annotation 사용하는 쪽에서 반드시 값 세팅 해줘야 한다.
    //String name();

    // 기본 값은 아래와 같이 주면 된다.
    // 기본 값은 사용하는 쪽에서 굳이 값을 안줘도 된다.
    String nameDefault() default "seungmoo";

    // value 라는 값으로 선언한 경우, 사용하는 쪽에서 값 세팅 시 field name 생략 가능하다.
    // BUT 여러개 속성 설정 시, field name 줘야 한다.
    String value();

}
