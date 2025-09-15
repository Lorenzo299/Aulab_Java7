package it.aulab.spring_data_project.repositories;

import org.springframework.data.repository.CrudRepository;

import it.aulab.spring_data_project.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
