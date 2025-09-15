package it.aulab.spring_data_project.repositories;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.spring_data_project.models.Comment;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {

}
