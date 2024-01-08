package com.devtiro.database;

import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil(){

    }

    public static AuthorEntity createTestAuthorA() {
        AuthorEntity author = AuthorEntity.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
        return author;
    }
    public static AuthorEntity createTestAuthorB() {
        AuthorEntity author = AuthorEntity.builder()
                .id(2L)
                .name("Peter Rose")
                .age(30)
                .build();
        return author;
    }
    public static AuthorEntity createTestAuthorC() {
        AuthorEntity author = AuthorEntity.builder()
                .id(3L)
                .name("Jessey Case")
                .age(40)
                .build();
        return author;
    }



    public static BookEntity createTestBookA(final AuthorEntity author) {
        BookEntity book = BookEntity.builder()
                .isbn("1234")
                .author(author)
                .title("The Best Book")
                .build();
        return book;
    }
    public static BookEntity createTestBookB(final AuthorEntity author) {
        BookEntity book = BookEntity.builder()
                .isbn("2345")
                .author(author)
                .title("The Best Book")
                .build();
        return book;
    }
    public static BookEntity createTestBookC(final AuthorEntity author) {
        BookEntity book = BookEntity.builder()
                .isbn("3456")
                .author(author)
                .title("The Best Book")
                .build();
        return book;
    }
}


