package it.aulab.spring_data_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.aulab.spring_data_project.models.Comment;
import it.aulab.spring_data_project.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment create(Comment create) {

        return commentRepository.save(create);
    }

    @Override
    public void delete(Long id) {

        commentRepository.deleteById(id);

    }

    @Override
    public Comment read(Long id) {

        Optional<Comment> comment = commentRepository.findById(id);
        return comment.get();
    }

    @Override
    public List<Comment> readAll() {

        return commentRepository.findAll();
    }

    @Override
    public Comment update(Long id, Comment comment) {

        comment.setId(id);
        return commentRepository.save(comment);
    }

}
