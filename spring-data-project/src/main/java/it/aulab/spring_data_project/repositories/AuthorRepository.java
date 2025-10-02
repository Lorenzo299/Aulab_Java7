package it.aulab.spring_data_project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import it.aulab.spring_data_project.models.Author;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
    List<Author> findByName(String name);

    List<Author> findBySurname(String surname);

    List<Author> findByNameAndSurname(String name, String surname);

    List<Author> findByEmail(String email);

    @Query(value = "SELECT * FROM authors a WHERE a.firstname = 'lollo'", nativeQuery = true)
    List<Author> authorsWithSameName();

    @Query(value = "SELECT a FROM Author a WHERE a.name = 'lollo'")
    List<Author> authorsWithSameNameNonNative();
}
