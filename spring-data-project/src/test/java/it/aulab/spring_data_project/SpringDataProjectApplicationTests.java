package it.aulab.spring_data_project;

import static org.assertj.core.api.Assertions.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import it.aulab.spring_data_project.models.Author;
import it.aulab.spring_data_project.repositories.AuthorRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SpringDataProjectApplicationTests {
	@Autowired
	AuthorRepository authorRepository;

	@BeforeEach
	void load() {
		Author a1 = new Author();
		a1.setName("lollo");
		a1.setSurname("micio");
		a1.setEmail("lollo@micio.it");
		authorRepository.save(a1);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void findByName() {
		assertThat(authorRepository.findByName("lollo")).extracting("name").containsOnly("lollo");
	}

	@Test
	void sameNameAuthor() {
		assertThat(authorRepository.authorsWithSameName()).extracting("name").containsOnly("lollo");
	}

	@Test
	void sameNameAuthorNonNative() {
		assertThat(authorRepository.authorsWithSameNameNonNative()).extracting("name").containsOnly("lollo");
	}

}
