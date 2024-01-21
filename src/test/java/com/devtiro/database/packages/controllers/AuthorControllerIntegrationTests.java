package com.devtiro.database.packages.controllers;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.entities.AuthorEntity;
import com.devtiro.database.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final AuthorService authorService;
    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc , ObjectMapper objectMapper, AuthorService authorService){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @Test
    public void testThatAuthorIsCreatedAnReturned() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        String createAuthorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors").
                        contentType(MediaType.APPLICATION_JSON).
                        content(createAuthorJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        );


    }

    @Test
    public void testThatListAuthorReturnsListOfAuthors() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get(
                        "/authors"
                ).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }

    @Test
    public void testThatListAuthorsReturnHttpStatus200() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


@Test
    public void testThatGetAuthorReturnHttpStatus200WhenAuthorExist() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
}
    @Test
    public void testThatGetAuthorReturnHttpStatus200WhenDoesNotExist() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatGetAuthorReturnsCorrectAuthor() throws Exception{
        AuthorEntity authorEntity =TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(80)
                )

        ;
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception{
        AuthorDto testAuthorDtoA  = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)).andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception{
        AuthorDto testAuthorDtoA  = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)).andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttp201Status() throws Exception{
        AuthorEntity testAuthorEntityA  = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception{
        AuthorEntity testAuthorEntityA  = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        testAuthorDtoA.setId(savedAuthor.getId());
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedAuthor.getName()));

    }
}
