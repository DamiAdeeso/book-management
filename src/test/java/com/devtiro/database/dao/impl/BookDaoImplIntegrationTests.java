package com.devtiro.database.dao.impl;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;

    private AuthorDaoImpl authorTest;

    @Autowired
    public BookDaoImplIntegrationTests (BookDaoImpl underTest , AuthorDaoImpl authorTest){
        this.authorTest = authorTest;
        this.underTest = underTest;
    }

    @Test
    public void  testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();

        authorTest.create(author);

        Book book = TestDataUtil.createTestBook();
       book.setAuthorId(author.getId());



        underTest.create(book);

        Optional<Book> result = underTest.find0ne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

}
