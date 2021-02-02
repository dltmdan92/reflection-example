package com.seungmoo.di;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerServiceTest {

    /**
     * @Inject 미사용 Test
     */
    @Test
    public void getObject_BookRepository() {
        BookRepository bookRepository = ContainerService.getObject(BookRepository.class);
        assertNotNull(bookRepository);
    }

    /**
     * @Inject 사용 Test
     *
     * 스프링의 DI를 구현해보자 (IOC 컨테이너를 만들어 보는 TEST)
     *
     * 이제 이 프로젝트를 mvn install 해서 local maven 저장소에 설치하고
     * 다른 프로젝트에서 직접 써보자!
     */
    @Test
    public void getObject_BookService() {
        BookService bookService = ContainerService.getObject(BookService.class);
        assertNotNull(bookService);
        assertNotNull(bookService.bookRepository);
    }
}