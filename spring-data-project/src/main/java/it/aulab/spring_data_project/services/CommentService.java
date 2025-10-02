package it.aulab.spring_data_project.services;

import java.util.List;

import it.aulab.spring_data_project.models.Comment;

public interface CommentService {

    List<Comment> readAll();

    Comment read(Long id);

    Comment update(Long id, Comment comment);

    Comment create(Comment create);

    void delete(Long id);

}
