package com.devtiro.database.repositories;

import com.devtiro.database.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Iterable<Author> ageLessThan(int age);


    @Query("SELECT a from Author a where a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan( int age);

}
