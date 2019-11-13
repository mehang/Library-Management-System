package com.baylor.se.lms.controller;

import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.service.impl.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @GetMapping(path = "/authors", produces="application/json")
    public ResponseEntity<List<Author>>getAuthors(){
        List<Author> authors = authorService.getAuthors();
        return ResponseEntity.ok().body(authors);
    }
    @GetMapping(path="/authors/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok().body(author);
    }

    @PostMapping(path="/authors/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        Author registeredAuthor = authorService.registerAuthor(author);
        return ResponseEntity.ok().body(registeredAuthor);
    }

    @PutMapping(path="/authors/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        authorService.updateAuthor(author);
        return ResponseEntity.ok().body(author);
    }

}
