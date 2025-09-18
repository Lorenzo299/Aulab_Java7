package it.aulab.spring_data_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.aulab.spring_data_project.models.Post;
import it.aulab.spring_data_project.repositories.PostRepository;

@RestController
@RequestMapping("/posts")

public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

}
