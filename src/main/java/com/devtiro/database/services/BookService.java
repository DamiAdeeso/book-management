package com.devtiro.database.services;

import com.devtiro.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookService {
    public BookEntity save(BookEntity book, String isbn);

    List<BookEntity> findAll();
}
