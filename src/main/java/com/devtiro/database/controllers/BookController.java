package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.domain.mappers.Mapper;
import com.devtiro.database.services.BookService;
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
    public ResponseEntity<BookDto>  createUpdateBook(@PathVariable("isbn") String isbn ,@RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity, isbn);
        BookDto savedBookDto = bookMapper.mapTo(savedBook);
       if(bookExists){
           return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
       }else {

          
           return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
       }
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ){

        if(!bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            BookEntity bookEntity = bookMapper.mapFrom(bookDto);
            BookEntity updatedBookEntity = bookService.partialUpdate(isbn,bookEntity);
            return new ResponseEntity<>(
                    bookMapper.mapTo(updatedBookEntity),
                    HttpStatus.OK
            );
        }
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

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(
            @PathVariable(name = "isbn") String isbn
    ){
        bookService.delete(isbn);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
