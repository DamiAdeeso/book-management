package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.domain.mappers.Mapper;
import com.devtiro.database.services.BookService;
import com.devtiro.database.services.impl.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    Mapper<BookEntity, BookDto> bookMapper;
    BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto>  createBook(@PathVariable("isbn") String isbn ,@RequestBody BookDto bookDto){
        BookEntity bookEntity  = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.save(bookEntity, isbn);
        BookDto  savedBookDto = bookMapper.mapTo(savedBook);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks(){
        List<BookEntity> books = bookService.findAll();

        return books.stream()
                .map(bookMapper :: mapTo)
                .collect(Collectors.toList());
    }
}
