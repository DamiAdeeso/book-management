package com.devtiro.database.services;

import com.devtiro.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookService {
    public BookEntity save(BookEntity book, String isbn);
}
