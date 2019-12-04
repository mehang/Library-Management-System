package com.baylor.se.lms.controller;

import com.baylor.se.lms.data.PasswordResetTokenRepository;
import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.dto.factory.AdminFactory;
import com.baylor.se.lms.dto.factory.LibrarianFactory;
import com.baylor.se.lms.dto.factory.StudentFactory;
import com.baylor.se.lms.exception.UnmatchingPasswordException;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    PasswordResetTokenRepository tokenRepository;

    @Autowired
    EmailService emailService;

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

    @PostMapping(path = "/users/forgot-password", consumes = "application/json")
    public ResponseEntity sendResetLink(@RequestBody PasswordForgotDTO passwordForgotDTO) {
        Optional<User> optionalUser = userService.findByEmail(passwordForgotDTO.getEmail());
        if (optionalUser.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email address.");
        }
        User user = optionalUser.get();
        PasswordResetToken token = userService.createPasswordResetToken(user);

        Mail mail = new Mail();
        mail.setFrom("mehang.rai007@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "http://lms.com");
        model.put("resetUrl", "http://localhost:3000" + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail);

        return ResponseEntity.status(HttpStatus.OK).body("Password reset link sent to the email address.");
    }

    @PostMapping(path="/users/reset-password", consumes = "application/json")
    public ResponseEntity resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        if (!passwordResetDTO.getPassword1().equals(passwordResetDTO.getPassword2())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password1 and password2 don't match with each other.");
        }
        userService.resetPassword(passwordResetDTO.getResetToken(),passwordResetDTO.getPassword1());
        return ResponseEntity.ok().body("Password has ben reset.");
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
        User registeredStudent = studentService.registerUser(userDTO);
        return ResponseEntity.ok().body(registeredStudent);
    }

    @PutMapping(path = "/users/students/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateStudent(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedStudent = studentService.updateUser(userUpdateDTO);
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
        User registeredLibrarian = librarianService.registerUser(userDTO);
        return ResponseEntity.ok().body(registeredLibrarian);
    }

    @PutMapping(path = "/users/librarians/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateLibrarian(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedLibrarian = librarianService.updateUser(userUpdateDTO);
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
        User registeredAdmin = adminService.registerUser(userDTO);
        return ResponseEntity.ok().body(registeredAdmin);
    }

    @PutMapping(path = "/users/admins/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateAdmin(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedAdmin = studentService.updateUser(userUpdateDTO);
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
    public ResponseEntity<UserVerifyDTO> verifyStudent(@PathVariable Long id) {
        BufferedReader reader;
        UserVerifyDTO verifyDTO = new UserVerifyDTO();
        verifyDTO.setUserId(id);
        try {
            reader = new BufferedReader(new FileReader("src\\main\\resources\\students.txt"));
            String line = reader.readLine();
            while (null != line) {
                if (line.equals(id.toString())) {
                    verifyDTO.setVerified(true);
                    return ResponseEntity.ok().body(verifyDTO);
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            verifyDTO.setVerified(false);
            return ResponseEntity.ok().body(verifyDTO);

        }
        verifyDTO.setVerified(false);
        return ResponseEntity.ok().body(verifyDTO);

    }
}
