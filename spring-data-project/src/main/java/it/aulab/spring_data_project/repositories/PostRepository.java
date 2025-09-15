package it.aulab.spring_data_project.repositories;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.spring_data_project.models.Post;

public interface PostRepository extends ListCrudRepository<Post, Long> {

}
