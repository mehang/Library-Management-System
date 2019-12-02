package com.baylor.se.lms.controller;

import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.service.impl.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@Slf4j
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @GetMapping(path = "/authors", produces="application/json")
    public ResponseEntity<List<Author>>getAuthors(){

        List<Author> authors = authorService.getAuthors();
        log.info(" Fetched all authors");
        return ResponseEntity.ok().body(authors);
    }
    @GetMapping(path="/authors/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthor(id);
        log.info("Fetched authors " + author.getName());
        return ResponseEntity.ok().body(author);
    }

    @PostMapping(path="/authors/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        log.info("Add a author: ");
        Author registeredAuthor = authorService.registerAuthor(author);
        log.info("Author added: " + registeredAuthor.getName());
        return ResponseEntity.ok().body(registeredAuthor);
    }

    @PutMapping(path="/authors/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        log.info("Update author: " + id);
        Author updatedAuthor = authorService.updateAuthor(author);
        log.info("Author updated: "+ updatedAuthor.getName());
        return ResponseEntity.ok().body(updatedAuthor);
    }

    @DeleteMapping(path="/authors/{id:[0-9][0-9]*}")
    public ResponseEntity deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        log.info("Author deleted: "+ id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
