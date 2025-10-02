package it.aulab.spring_data_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.aulab.spring_data_project.models.Author;
import it.aulab.spring_data_project.services.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.readAll();
    }

    @GetMapping("{id}")
    public Author getAuthor(@PathVariable("id") Long id) {
        return authorService.read(id);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.create(author);
    }

    @PutMapping("{id}")
    public Author updateAuthor(@PathVariable("id") Long id, @RequestBody Author author) {
        return authorService.update(id, author);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
    }
}
