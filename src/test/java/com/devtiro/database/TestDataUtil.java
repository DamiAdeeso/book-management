package com.devtiro.database;

import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){

    }

    public static Author createTestAuthor() {
        Author author = Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
        return author;
    }

    public static Book createTestBook() {
        Book book = Book.builder()
                .isbn("1234")
                .authorId(1L)
                .title("The Best Book")
                .build();
        return book;
    }
}


