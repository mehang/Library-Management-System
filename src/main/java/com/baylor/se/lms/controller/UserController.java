package com.baylor.se.lms.controller;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.service.impl.AdminService;
import com.baylor.se.lms.service.impl.LibrarianService;
import com.baylor.se.lms.service.impl.StudentService;
import com.baylor.se.lms.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    LibrarianService librarianService;

    @Autowired
    AdminService adminService;

//    @GetMapping(path="/users", produces = "application/json")
//    public ResponseEntity<User> getUserByUsername(){
//        //todo: check this all
////        User user = userService.getUserByUsername("abc");
//        return ResponseEntity.ok().body(user);
//    }
//
//    @GetMapping(path="/users/{id:[0-9][0-9]*}", produces = "application/json")
//    public ResponseEntity<User> getUserById(@PathVariable Long id){
////        User user = userService.getUserByUsername("abc");
//        return ResponseEntity.ok().body(user);
//    }

    //todo: look for this
    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping(path = "/users/students", produces="application/json")
    public ResponseEntity<List<Student>>getStudents(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping(path="/users/students/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<User> getStudentById(@PathVariable Long id) {
        User student = studentService.getUser(id);
        return ResponseEntity.ok().body(student);
    }

    @PostMapping(path="/users/students",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<User> addStudent(@RequestBody UserDTO userDTO) {
        Student student = new Student();
        student.setUsername(userDTO.getUsername());
        student.setEmail(userDTO.getEmail());
        student.setPassword(userDTO.getPassword());
        student.setName(userDTO.getName());
        student.setPhoneNumber(userDTO.getPhoneNumber());
        Role role = new Role();
        role.setRole("ROLE_STUDENT");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        student.setRoles(roles);
        User registeredStudent = studentService.registerUser(student);
        return ResponseEntity.ok().body(registeredStudent);
    }

//    @GetMapping("/login")
//    public String login(Model model, String error, String logout) {
//        if (error != null)
//            model.addAttribute("error", "Your username and password is invalid.");
//
//        if (logout != null)
//            model.addAttribute("message", "You have been logged out successfully.");
//
//        return "login";
//    }

    @PutMapping(path="/users/students/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        User updatedStudent = studentService.updateUser(student);
        return ResponseEntity.ok().body(updatedStudent);
    }

    //todo: delete

    @GetMapping(path = "/users/librarians", produces="application/json")
    public ResponseEntity<List<Librarian>>getLibrarians(){
        List<Librarian> librarians = librarianService.getAll();
        return ResponseEntity.ok().body(librarians);
    }

    @GetMapping(path="/users/librarians/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<User> getLibrarianById(@PathVariable Long id) {
        User librarian = librarianService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }
    @PostMapping(path="/users/librarians",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<User> addLibrarian(@RequestBody Librarian librarian) {
        User registeredLibrarian = librarianService.registerUser(librarian);
        return ResponseEntity.ok().body(registeredLibrarian);
    }

    @PutMapping(path="/users/librarians/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> updateLibrarian(@RequestBody Librarian librarian, @PathVariable Long id) {
        User updatedLibrarian = studentService.updateUser(librarian);
        return ResponseEntity.ok().body(updatedLibrarian);
    }

    //todo: add delete

    @GetMapping(path = "/users/admins", produces="application/json")
    public ResponseEntity<List<Admin>>getAdmins(){
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.ok().body(admins);
    }

    @GetMapping(path="/users/admins/{id:[0-9][0-9]*}", produces="application/json")
    public ResponseEntity<User> getAdminById(@PathVariable Long id) {
        User librarian = librarianService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }
    @PostMapping(path="/users/admins",consumes = "application/json", produces="application/json")
    @ResponseBody
    public ResponseEntity<User> addAdmin(@RequestBody Admin admin) {
        User registeredAdmin = adminService.registerUser(admin);
        return ResponseEntity.ok().body(registeredAdmin);
    }

    @PutMapping(path="/users/admins/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> updateAdmin(@RequestBody Admin admin, @PathVariable Long id) {
        User updatedAdmin = studentService.updateUser(admin);
        return ResponseEntity.ok().body(updatedAdmin);
    }

//todo: add delete
}
