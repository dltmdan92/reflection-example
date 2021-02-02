package com.seungmoo;

@MyAnnoation("seungmoo")
public class Book {
    private String a = "a";
    private static String B = "BOOK";
    private static final String C = "BOOK";
    public String d = "d";
    protected String e = "e";

    public Book() {
    }

    @FieldAnnotation("Sam Moo")
    public Book(String a, String d, String e) {
        this.a = a;
        this.d = d;
        this.e = e;
    }

    @FieldAnnotation("Sam Moo")
    private void f() {
        System.out.println("F");
    }

    @FieldAnnotation("Sam Moo")
    public void g() {
        System.out.println("g");
    }

    public Integer h() {
        return 100;
    }
}
