package com.seungmoo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, NoSuchFieldException {

        // 기본적으로 Reflection은 Class 라는 객체에서 시작된다.
        // 아래 대표적인 3가지 방법으로 ClassLoading된 객체에 접근할 수 있다.

        // 방법 1
        Class<Book> bookClass = Book.class;

        // 방법 2
        Book book = new Book();
        Class<? extends Book> aClass = book.getClass();

        // 방법 3
        Class<?> aClass1 = Class.forName("com.seungmoo.Book");

        // 클래스의 필드를 불러오기
        // getFields : public한 field만 갖고 온다.
        Field[] fields = bookClass.getFields();

        // getDeclaredFields : 선언된 모든 field들을 접근해서 갖고 온다.
        Field[] declaredFields = bookClass.getDeclaredFields();

        // 특정 선언 필드만 갖고 오기
        Field declaredField = bookClass.getDeclaredField("a");

        Arrays.stream(declaredFields).forEach(f -> {
            try {
                // BUT!!! private 한 필드의 경우, IllegalAccessException 발생한다.
                // setAccessible을 true로 주면 접근이 가능하게 된다.
                f.setAccessible(true);
                System.out.printf("%s %s", f, f.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        // 메서드 갖고 오기
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

        // 생성자 갖고 오기
        Arrays.stream(bookClass.getConstructors()).forEach(System.out::println);

        // superClass 갖고 오기
        System.out.println(MyBook.class.getSuperclass());

        // 구현하는 인터페이스 갖고 오기
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);

        Arrays.stream(declaredFields).forEach(f -> {
            int modifiers = f.getModifiers();
            System.out.println(f);
            System.out.println(modifiers);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));
        });

        // 그냥 이렇게 했을 경우에는 Book에 있는 Annotation을 못 불러온다.
        // BUT) @Retention(RetentionPolicy.RUNTIME) 이 붙어있는 Annotaion은 Runtime 시 메모리에 있기 때문에 접근 가능함.
        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);

        // super class에서 선언된 Annotation도 갖고 올 수 있다. (@Inherited Annotation의 경우)
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);

        // MyBook에 선언된 Annotation만 갖고 온다.
        Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println);

        // field에 붙은 Annotation을 가져와 보자.
        Arrays.stream(MyBook.class.getDeclaredFields()).forEach( f -> {
            Arrays.stream(f.getAnnotations()).forEach(a -> {
                if (a instanceof MyAnnoation) {
                    MyAnnoation myAnnoation = (MyAnnoation) a;
                    System.out.println(myAnnoation.value());
                    System.out.println(myAnnoation.nameDefault());
                }
            });
        });
    }
}
