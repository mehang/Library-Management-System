package com.baylor.se.lms.controller;

import com.baylor.se.lms.dto.PasswordChangeDTO;
import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.dto.UserUpdateDTO;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @Autowired
    BookLoanService bookLoanService;

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

    @PostMapping(path = "/users/change-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        Long id = passwordChangeDTO.getId();
        String newPassword = passwordChangeDTO.getPassword1();
        String userType = passwordChangeDTO.getUserType();
        if (!passwordChangeDTO.getPassword1().equals(passwordChangeDTO.getPassword2())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password1 and password2 don't match with each other.");
        }
        if (userType.equals("STUDENT")) {
            studentService.changePassword(id, newPassword);
        } else if (userType.equals("LIBRARIAN")) {
            librarianService.changePassword(id, newPassword);
        } else {
            adminService.changePassword(id, newPassword);
        }
        return ResponseEntity.ok().body(true);
    }

    //    @PreAuthorize("hasAnyRole('STUDENT','ROLE_STUDENT')")
    @GetMapping(path = "/users/students", produces = "application/json")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping(path = "/users/students/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity<User> getStudentById(@PathVariable Long id) {
        User student = studentService.getUser(id);
        return ResponseEntity.ok().body(student);
    }

    @PostMapping(path = "/users/students", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addStudent(@RequestBody UserDTO userDTO) {
        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password1 and password2 don't match with each other.");
        }
        Student student = (Student) convertDTOtoUser(userDTO, new Student());
        User registeredStudent = studentService.registerUser(student);
        return ResponseEntity.ok().body(registeredStudent);
    }

    @PutMapping(path = "/users/students/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateStudent(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        Student student = (Student) studentService.getUser(userUpdateDTO.getId());
        student.setUsername(userUpdateDTO.getUsername());
        student.setEmail(userUpdateDTO.getEmail());
        student.setName(userUpdateDTO.getName());
        student.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        User updatedStudent = studentService.updateUser(student);
        return ResponseEntity.ok().body(updatedStudent);
    }

    //todo: delete

    @GetMapping(path = "/users/librarians", produces = "application/json")
    public ResponseEntity getLibrarians() {
        List<Librarian> librarians = librarianService.getAll();
        return ResponseEntity.ok().body(librarians);
    }

    @GetMapping(path = "/users/librarians/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getLibrarianById(@PathVariable Long id) {
        User librarian = librarianService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }

    @PostMapping(path = "/users/librarians", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addLibrarian(@RequestBody UserDTO userDTO) {
        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password1 and password2 don't match with each other.");
        }
        Librarian librarian = (Librarian) convertDTOtoUser(userDTO, new Librarian());
        librarianService.registerUser(librarian);
        return ResponseEntity.ok().body(librarian);
    }

    @PutMapping(path = "/users/librarians/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateLibrarian(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        Librarian librarian = (Librarian) librarianService.getUser(userUpdateDTO.getId());
        librarian.setUsername(userUpdateDTO.getUsername());
        librarian.setEmail(userUpdateDTO.getEmail());
        librarian.setName(userUpdateDTO.getName());
        librarian.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        User updatedLibrarian = librarianService.updateUser(librarian);
        return ResponseEntity.ok().body(updatedLibrarian);
    }

    //todo: add delete

    @GetMapping(path = "/users/admins", produces = "application/json")
    public ResponseEntity getAdmins() {
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.ok().body(admins);
    }

    @GetMapping(path = "/users/admins/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getAdminById(@PathVariable Long id) {
        User librarian = librarianService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }

    @PostMapping(path = "/users/admins", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addAdmin(@RequestBody UserDTO userDTO) {
        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password1 and password2 don't match with each other.");
        }
        Admin admin = (Admin) convertDTOtoUser(userDTO, new Admin());
        User registeredAdmin = adminService.registerUser(admin);
        return ResponseEntity.ok().body(registeredAdmin);
    }

    @PutMapping(path = "/users/admins/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateAdmin(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        Admin admin = (Admin) adminService.getUser(userUpdateDTO.getId());
        admin.setUsername(userUpdateDTO.getUsername());
        admin.setEmail(userUpdateDTO.getEmail());
        admin.setName(userUpdateDTO.getName());
        admin.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        User updatedAdmin = studentService.updateUser(admin);
        return ResponseEntity.ok().body(updatedAdmin);
    }

//todo: add delete

    /**
     * Get BookLoan services according to user
     */
    @GetMapping(path = "/users/{username}/bookloans", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BookLoan>> getBookLoans(@PathVariable String username) {
        List<BookLoan> bookLoans = bookLoanService.getBookLoanByUser(username);
        return ResponseEntity.ok().body(bookLoans);
    }

    private User convertDTOtoUser(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword1());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
