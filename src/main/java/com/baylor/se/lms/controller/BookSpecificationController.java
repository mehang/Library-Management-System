package com.baylor.se.lms.controller;
import com.baylor.se.lms.model.BookSpecification;
import com.baylor.se.lms.service.impl.BookSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Book Specification Controller that handles all CRUD opeartions for BookSpecification entity
 */
@CrossOrigin(origins="*")
@RestController
public class BookSpecificationController {

    @Autowired
    BookSpecificationService bookSpecService;

    /**
     * Handles GET request for fetching all book specification.
     * @return Response wit list of book specification
     */
    @GetMapping(path="/bookspecs", produces="application/json")
    public ResponseEntity<List<BookSpecification>> getBookSpecs(){
        List<BookSpecification> bookSpeckList =  bookSpecService.getBookspec();
        return ResponseEntity.ok().body(bookSpeckList);
    }

    /**
     *  Handles GET request for fetching single book specification.
     * @param id :  Specification Id (Path variable)
     * @return Book Specification as JSON response
     */
    @GetMapping(path="/bookspecs/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<BookSpecification> getBookSpecs(@PathVariable Long id) {
        BookSpecification bookSpec = bookSpecService.getBookSpec(id);
        return ResponseEntity.ok().body(bookSpec);
    }

    /**
     *  Handles POST request to create new Book Specification
     * @param bookSpec : Book Spec to be saved
     * @return Saved Book Specification as JSON response
     */
    @PostMapping(path="/bookspecs/",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<BookSpecification> addBookSpec(@RequestBody BookSpecification bookSpec) {
        BookSpecification registeredBookSpec = bookSpecService.saveBookSpec(bookSpec);
        return ResponseEntity.ok().body(registeredBookSpec);
    }

    /**
     * Update Book Specification given by ID
     * @param bookSpecification : Book Specification to be updated
     * @return Updated book specification
     */
    @PutMapping(path="/bookspecs/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<BookSpecification> updateBook(@RequestBody BookSpecification bookSpecification) {
        BookSpecification updatedBookSpecification = bookSpecService.updateBookSpec(bookSpecification);
        return ResponseEntity.ok().body(updatedBookSpecification);
    }
}
