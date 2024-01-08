package com.devtiro.database.repositories;

import com.devtiro.database.TestDataUtil;
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
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;



    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest , AuthorRepository authorTest){

        this.underTest = underTest;
    }

    @Test
    public void  testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();


        Book book = TestDataUtil.createTestBookA(author);
       book.setAuthor(author);



        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){

        Author author = TestDataUtil.createTestAuthorB();
        Book bookA =TestDataUtil.createTestBookA(author);
        bookA.setAuthor(author);
        underTest.save(bookA);



        Book bookB = TestDataUtil.createTestBookB(author);
        bookB.setAuthor(author);
        underTest.save(bookB);


        Book bookC  = TestDataUtil.createTestBookC(author);
        bookC.setAuthor(author);
        underTest.save(bookC);

        Iterable<Book> results =  underTest.findAll();

        assertThat(results).hasSize(3).containsExactly(bookA, bookB,bookC);
    }


@Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();

        Book book = TestDataUtil.createTestBookA(author);
        book.setAuthor(author);
        underTest.save(book);

        book.setTitle("Updated");
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
}

@Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();


        Book book = TestDataUtil.createTestBookA(author);
        book.setAuthor(author);
        underTest.save(book);

        underTest.deleteById(book.getIsbn());

        Optional<Book> result = underTest.findById(book.getIsbn());

        assertThat(result).isEmpty();
}

}
