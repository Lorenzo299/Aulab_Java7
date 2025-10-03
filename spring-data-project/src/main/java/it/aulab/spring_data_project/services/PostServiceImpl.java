package it.aulab.spring_data_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.spring_data_project.models.Comment;
import it.aulab.spring_data_project.models.Post;
import it.aulab.spring_data_project.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        if (postRepository.existsById(id)) {
            Post post = postRepository.findById(id).get();
            List<Comment> comments = post.getComments();
            for (var comm : comments) {
                comm.setPost(null);
            }

            postRepository.delete(post);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found");
        }

    }

    @Override
    public Post read(Long Id) {

        Optional<Post> post = postRepository.findById(Id);
        return post.get();
    }

    @Override
    public Post read(String title) {

        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> readAll() {

        return postRepository.findAll();
    }

    @Override
    public Post update(Long id, Post post) {
        post.setId(id);
        return postRepository.save(post);
    }

}
