package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.domain.mappers.Mapper;
import com.devtiro.database.services.BookService;
import com.devtiro.database.services.impl.BookServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable(name="isbn") String isbn ){
        Optional<BookEntity> foundBook =bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);

            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
