package com.baylor.se.lms.controller;
import com.baylor.se.lms.model.BookSpecification;
import com.baylor.se.lms.service.impl.BookSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class BookSpecificationController {

    @Autowired
    BookSpecificationService bookSpecService;

    @GetMapping(path="/bookspecs", produces="application/json")
    public ResponseEntity<List<BookSpecification>> getBookSpecs(){
        List<BookSpecification> bookSpeckList =  bookSpecService.getBookspec();
        return ResponseEntity.ok().body(bookSpeckList);
    }

    @GetMapping(path="/bookspecs/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<BookSpecification> getBookSpecs(@PathVariable Long id) {
        BookSpecification bookSpec = bookSpecService.getBookSpec(id);
        return ResponseEntity.ok().body(bookSpec);
    }

    @PostMapping(path="/bookspecs/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<BookSpecification> addBookSpec(@RequestBody BookSpecification bookSpec) {
        BookSpecification registeredBookSpec = bookSpecService.saveBookSpec(bookSpec);
        return ResponseEntity.ok().body(registeredBookSpec);
    }

    @PutMapping(path="/bookspecs/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookSpecification> updateBook(@RequestBody BookSpecification bookSpecification) {
        bookSpecService.updateBookSpec(bookSpecification);
        return ResponseEntity.ok().body(bookSpecification);
    }

}
