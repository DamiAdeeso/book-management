package com.devtiro.database.packages.controllers;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookIsSuccessfulAndReturnsStatusCreated() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value((bookDto.getIsbn()))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpsStatus200() throws Exception{
        BookEntity testBookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(testBookEntity, testBookEntity.getIsbn());

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(testBookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(testBookEntity, testBookEntity.getIsbn());

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(testBookEntity.getIsbn());
        testBookDto.setTitle("Updated");
        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated"));

    }
    @Test
    public void testThatListBooksReturnHttpStatus200() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void testThatListBookReturnsListOfBooks() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        BookEntity book2 =TestDataUtil.createTestBookB(null);
        bookService.createUpdateBook(book, book.getIsbn() );
        bookService.createUpdateBook(book2, book2.getIsbn() );

        mockMvc.perform(
                MockMvcRequestBuilders.get(
                                "/books"
                        ).contentType(MediaType.APPLICATION_JSON_VALUE)


        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value(book.getTitle())
        );

    }

    @Test
    public void testThatFindBookReturnHttpStatus200() throws Exception {
    BookEntity bookEntity = TestDataUtil.createTestBookA(null);
    bookService.createUpdateBook(bookEntity,"1234");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/1234")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void testThatListBooksReturnHttpStatus404() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/1234")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    public void testThatGetAuthorReturnsCorrectBook() throws Exception{
        BookEntity bookEntity =TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity,"1234");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/1234")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Best Book")
                );
    }

    @Test
    public void testThatPartialUpdateReturnsHtppStatus200() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntity,"1234");

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(testBookEntity.getIsbn());
        testBookDto.setTitle("Updated");
        String bookJson = objectMapper.writeValueAsString(testBookDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/books/" + testBookEntity.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson))

                .andExpect(MockMvcResultMatchers.status().isOk());

    }
@Test
    public void testThatDeleteNonExistingBookReturnsHttpsStatus204()throws Exception{
    mockMvc.perform(
                    MockMvcRequestBuilders.delete("/books/23" )
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
}
    @Test
    public void testThatDeleteExistingBookReturnsHttpsStatus204()throws Exception{
        BookEntity bookEntity =TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity,"1234");

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/books/" +bookEntity.getIsbn() )
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
