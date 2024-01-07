package com.devtiro.database.dao.impl;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.dao.AuthorDao;
import com.devtiro.database.domain.Author;
import com.devtiro.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;

    private AuthorDaoImpl authorDao;

    @Autowired
    public BookDaoImplIntegrationTests (BookDaoImpl underTest , AuthorDaoImpl authorTest){
        this.authorDao = authorTest;
        this.underTest = underTest;
    }

    @Test
    public void  testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();

        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
       book.setAuthorId(author.getId());



        underTest.create(book);

        Optional<Book> result = underTest.find0ne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){

        Author author = TestDataUtil.createTestAuthorB();
        authorDao.create(author);
        Book bookA =TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);



        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);


        Book bookC  = TestDataUtil.createTestBookC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);

        List<Book> results =  underTest.find();

        assertThat(results).hasSize(3).containsExactly(bookA, bookB,bookC);
    }


@Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        book.setTitle("Updated");
        underTest.update(book.getIsbn(),book);

        Optional<Book> result = underTest.find0ne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
}

@Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> result = underTest.find0ne(book.getIsbn());

        assertThat(result).isEmpty();
}

}
