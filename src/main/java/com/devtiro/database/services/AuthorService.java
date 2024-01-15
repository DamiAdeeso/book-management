package com.devtiro.database.services;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {


    public AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}
