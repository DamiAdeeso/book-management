package com.devtiro.database.services.impl;

import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.repositories.BookRepository;
import com.devtiro.database.services.BookService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public BookEntity createUpdateBook(BookEntity book, String isbn) {
        book.setIsbn(isbn);
        BookEntity savedBook = bookRepository.save(book);

        return savedBook;
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport
                .stream(bookRepository.findAll().spliterator()
                        , false
                ).collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }


}
