package com.devtiro.database.controllers;


import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.domain.mappers.Mapper;
import com.devtiro.database.services.AuthorService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
      List<AuthorEntity> authors =   authorService.findAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path ="/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id" ) Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);

            return new ResponseEntity<>(authorDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable(name ="id") Long id, @RequestBody AuthorDto authorDto) {
        if (!authorService.isExists(id)) {
            System.out.println("print here");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthor =  authorService.save(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }
    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable(name = "id") Long id,
            @RequestBody AuthorDto authorDto
    ){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
           AuthorEntity updatedEntity =  authorService.partialUpdate(id,authorEntity);

           return new ResponseEntity<>(authorMapper.mapTo(updatedEntity),HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(
            @PathVariable(name = "id") Long id
    ){
       authorService.delete(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}