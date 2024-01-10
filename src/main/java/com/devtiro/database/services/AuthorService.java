package com.devtiro.database.services;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {


    public AuthorEntity save(AuthorEntity authorEntity);
}
