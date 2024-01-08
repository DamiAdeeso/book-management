package com.devtiro.database;

import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){

    }

    public static Author createTestAuthorA() {
        Author author = Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
        return author;
    }
    public static Author createTestAuthorB() {
        Author author = Author.builder()
                .id(2L)
                .name("Peter Rose")
                .age(30)
                .build();
        return author;
    }
    public static Author createTestAuthorC() {
        Author author = Author.builder()
                .id(3L)
                .name("Jessey Case")
                .age(40)
                .build();
        return author;
    }



    public static Book createTestBookA(final Author author) {
        Book book = Book.builder()
                .isbn("1234")
                .author(author)
                .title("The Best Book")
                .build();
        return book;
    }
    public static Book createTestBookB(final Author author) {
        Book book = Book.builder()
                .isbn("2345")
                .author(author)
                .title("The Best Book")
                .build();
        return book;
    }
    public static Book createTestBookC(final Author author) {
        Book book = Book.builder()
                .isbn("3456")
                .author(author)
                .title("The Best Book")
                .build();
        return book;
    }
}


