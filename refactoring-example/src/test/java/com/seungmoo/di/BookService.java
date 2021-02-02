package com.seungmoo.di;

public class BookService {
    // @Inject annotation을 통해 객체를 주입 받는
    // 일종의 DI를 구현해보자
    @Inject
    BookRepository bookRepository;
}
