package com.devtiro.database.dao.impl;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.dao.impl.BookDaoImpl;
import com.devtiro.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void  testBookCreateMethodGeneratesCorrectSql(){

        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

         verify(jdbcTemplate).update(
                 eq("INSERT INTO books(isbn, title, author_id) VALUES (?, ?, ?)"),
                 eq("1234"),
                 eq("The Best Book"),
                 eq(1L)
         );

    }



    @Test
    public void testThatFindOneReturnsCorrectSql(){
        underTest.find0ne("1234");

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"), ArgumentMatchers.<BookDaoImpl.BookClassMapper>any(),eq("1234"));
    }

    @Test
    public void testThatFindReturnCorrectSQL(){
        underTest.find();

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"), ArgumentMatchers.<BookDaoImpl.BookClassMapper>any());
    }

    @Test
    public void testThatUpdateBookGeneratesCorrectSQL(){
        Book book = TestDataUtil.createTestBookA();

        underTest.update("1234",book);
        verify(jdbcTemplate).update("UPDATE books SET  isbn = ?, title = ?, author_id =? WHERE isbn = ?"
        ,"1234","The Best Book", 1L, book.getIsbn());



    }

    @Test
    public void testThatDeleteBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.delete("1234");
        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ? ", "1234");
    }

}
