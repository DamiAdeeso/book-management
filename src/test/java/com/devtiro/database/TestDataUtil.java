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
                .age(90)
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



    public static Book createTestBookA() {
        Book book = Book.builder()
                .isbn("1234")
                .authorId(1L)
                .title("The Best Book")
                .build();
        return book;
    }
    public static Book createTestBookB() {
        Book book = Book.builder()
                .isbn("2345")
                .authorId(2L)
                .title("The Best Book")
                .build();
        return book;
    }
    public static Book createTestBookC() {
        Book book = Book.builder()
                .isbn("3456")
                .authorId(3L)
                .title("The Best Book")
                .build();
        return book;
    }
}


