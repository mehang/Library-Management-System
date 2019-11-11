package com.baylor.se.lms.controller;

import com.baylor.se.lms.model.Author;
import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.impl.StudentService;
import com.baylor.se.lms.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @GetMapping(path="/users", produces = "application/json")
    public ResponseEntity<User> getUserByUsername(){
        User user = userService.getUserByUsername("abc");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(path="/users/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserByUsername("abc");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(path = "/users/students", produces="application/json")
    public ResponseEntity<List<Student>>getAuthors(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok().body(students);
    }
    @GetMapping(path="/users/students/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User student = studentService.getUser(id);
        return ResponseEntity.ok().body(student);
    }
    @PostMapping(path="/users/students",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<User> addBook(@RequestBody User student) {
        User registeredStudent = studentService.registerUser(student);
        return ResponseEntity.ok().body(registeredStudent);
    }

    @PutMapping(path="/authors/students/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> updateBook(@RequestBody User student) {
        User updatedStudent = studentService.updateUser(student);
        return ResponseEntity.ok().body(updatedStudent);
    }

}
