package com.devtiro.database.services.impl;

import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.domain.mappers.Mapper;
import com.devtiro.database.repositories.BookRepository;
import com.devtiro.database.services.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }



    @Override
    public BookEntity save(BookEntity book, String isbn) {
        book.setIsbn(isbn);
        BookEntity savedBook = bookRepository.save(book);

        return savedBook;
    }
}
