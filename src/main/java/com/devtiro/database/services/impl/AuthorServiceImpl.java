package com.devtiro.database.services.impl;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.domain.mappers.Mapper;
import com.devtiro.database.repositories.AuthorRepository;
import com.devtiro.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, Mapper<AuthorEntity, AuthorDto> authorMapper){

        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }


    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return  authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
       return StreamSupport.stream( authorRepository.
                       findAll()
                       .spliterator(),
                       false)
               .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(long id) {
       return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);
       return  authorRepository.findById(id).map(existingAuthor->{
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor ::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
           return  authorRepository.save(existingAuthor);
        }).orElseThrow(()-> new RuntimeException("Author Does not update"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
