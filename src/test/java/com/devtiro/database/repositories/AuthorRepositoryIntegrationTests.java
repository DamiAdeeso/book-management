package com.devtiro.database.repositories;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }


    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<Author> results = underTest.findAll();

        assertThat(results).hasSize(3)
                .containsExactly(authorA, authorB, authorC);


    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);

        authorA.setName("Updated");
        authorA.setAge(9);

        underTest.save(authorA);

        Optional<Author> result = underTest.findById(authorA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
            Author author = TestDataUtil.createTestAuthorA();
            underTest.save(author);

            underTest.deleteById(author.getId());

            Optional<Author> result = underTest.findById(author.getId());
            assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        Author authorA  = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.ageLessThan(50);

        assertThat(result).containsExactly(authorB,authorC);
    }


@Test
    public void testThatGetAuthorsGreaterThan(){
    Author authorA  = TestDataUtil.createTestAuthorA();
    underTest.save(authorA);
    Author authorB = TestDataUtil.createTestAuthorB();
    underTest.save(authorB);
    Author authorC = TestDataUtil.createTestAuthorC();
    underTest.save(authorC);


    Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(50);

    assertThat(result).containsExactly(authorA);
}


}

