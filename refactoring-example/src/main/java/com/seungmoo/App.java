package com.seungmoo;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //reflection1();

        reflection2();
    }

    private static void reflection1() throws ClassNotFoundException, NoSuchFieldException {
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

    private static void reflection2() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class<?> bookClass = Class.forName("com.seungmoo.Book2");

        // String을 Parameter로 받는 생성자를 갖고 온다.
        Constructor<?> constructor = bookClass.getConstructor(String.class);
        // 생성자를 통해 새로운 객체를 생성한다.
        Book2 book2 = (Book2) constructor.newInstance("myBook");

        System.out.println(book2);

        Field a = Book2.class.getDeclaredField("A");
        System.out.println(a.get(null));
        // A는 static한 필드이므로 참조 객체는 필요없다. 그냥 null 넣는다.
        a.set(null, "BBBB");
        System.out.println(a.get(null));

        // B는 인스턴스의 field이므로 참조되는 인스턴스가 존재해야 한다. 그냥 Field를 갖고 올 경우 ERROR 발생!!
        //Field b = Book2.class.getDeclaredField("B");

        // b는 인스턴스 변수 이므로, 위에서 생성된 book2 인스턴스 객체에서 갖고 오도록 한다.
        Field b = book2.getClass().getDeclaredField("B");
        b.setAccessible(true);
        System.out.println(b.get(book2));
        b.set(book2, "AAAA");
        System.out.println(b.get(book2));

        // 그냥 getMethod() 할 경우, private 한 거는 안갖고 온다.
        Method db = Book2.class.getDeclaredMethod("c");
        // getDeclaredMethod를 통해 private method를 갖고 왔으나, 조작은 실패한다.
        // 그 때 setAccessible을 해주면 private 또한 접근이 가능하게 된다.
        db.setAccessible(true);
        // db.invoke(book2, "book");  Argument 갯수 실패
        db.invoke(book2);

        // int parameter 두개를 받는 class
        // primitive type과 reference type 구분하므로 맞춰서 셋팅 해줄 것.
        Method sum = Book2.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book2, 1, 2);
        System.out.println(invoke);

    }
}
