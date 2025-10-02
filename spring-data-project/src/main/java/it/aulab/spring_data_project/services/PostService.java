package it.aulab.spring_data_project.services;

import java.util.List;

import it.aulab.spring_data_project.models.Post;

public interface PostService {

    List<Post> readAll();

    Post read(Long Id);

    Post read(String title);

    Post create(Post post);

    Post update(Long id, Post post);

    void delete(Long id);
}
