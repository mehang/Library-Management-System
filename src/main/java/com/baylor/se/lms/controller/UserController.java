package com.baylor.se.lms.controller;

import com.baylor.se.lms.dto.PasswordChangeDTO;
import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.dto.UserUpdateDTO;
import com.baylor.se.lms.dto.UserVerifyDTO;
import com.baylor.se.lms.dto.factory.AdminFactory;
import com.baylor.se.lms.dto.factory.LibrarianFactory;
import com.baylor.se.lms.dto.factory.StudentFactory;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

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
        Student student=(Student) studentService.registerUser(userDTO);
        return ResponseEntity.ok().body(student);
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
        Librarian librarian=(Librarian) librarianService.registerUser(userDTO);
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
        Admin admin=(Admin) adminService.registerUser(userDTO);
        return ResponseEntity.ok().body(admin);
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

    @GetMapping(path = "/users/students/verify/{id:[0-9][0-9]*}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<UserVerifyDTO> verifyStudent(@PathVariable Long id){
        BufferedReader reader;
        UserVerifyDTO verifyDTO =  new UserVerifyDTO();
        verifyDTO.setUserId(id);
        try {
            reader =  new BufferedReader(new FileReader("src\\main\\resources\\students.txt"));
            String line = reader.readLine();
            while(null != line){
                if (line.equals(id.toString())){
                    verifyDTO.setVerified(true);
                    return ResponseEntity.ok().body(verifyDTO);
                }
                line =  reader.readLine();
            }
        } catch (Exception e) {
            verifyDTO.setVerified(false);
            return ResponseEntity.ok().body(verifyDTO);

        }
        verifyDTO.setVerified(false);
        return  ResponseEntity.ok().body(verifyDTO);

    }
}
