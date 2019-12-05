package com.baylor.se.lms.controller;

import com.baylor.se.lms.data.PasswordResetTokenRepository;
import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.dto.user.create.StudentCreateDTO;
import com.baylor.se.lms.dto.user.create.UserCreateDTO;
import com.baylor.se.lms.dto.user.update.StudentUpdateDTO;
import com.baylor.se.lms.dto.user.update.UserUpdateDTO;
import com.baylor.se.lms.exception.UnmatchingPasswordException;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.security.TokenProvider;
import com.baylor.se.lms.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BookLoanService bookLoanService;

    @Autowired
    PasswordResetTokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody LoginDTO loginUser) throws AuthenticationException {
        log.info("Authenticate:  " + loginUser.getUsername());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        Optional<User> user = userService.findByUsername(loginUser.getUsername());
        Map<Object, Object> authModel = new HashMap<>();
        if (user.isPresent()){
            User loggedUser = user.get();
            authModel.put("token", token);
            authModel.put("username", loggedUser.getUsername());
            authModel.put("userPK", loggedUser.getId());
            authModel.put("type", loggedUser.getDiscriminatorValue());
        }
        return ResponseEntity.ok(authModel);
    }

    @PostMapping(path = "/users/change-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO);
        return ResponseEntity.ok().body("Password has been changed successfully.");
    }

    @PostMapping(path = "/users/forgot-password", consumes = "application/json")
    public ResponseEntity sendResetLink(@RequestBody PasswordForgotDTO passwordForgotDTO) {
        userService.createPasswordResetToken(passwordForgotDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password reset link sent to the email address.");
    }

    @PostMapping(path="/users/reset-password", consumes = "application/json")
    public ResponseEntity resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        userService.resetPassword(passwordResetDTO);
        return ResponseEntity.ok().body("Password has been reset.");
    }

    //    @PreAuthorize("hasAnyRole('STUDENT','ROLE_STUDENT')")
    @GetMapping(path = "/users/students", produces = "application/json")
    public ResponseEntity getStudents() {
        List<User> students = userService.getAll(UserService.UserType.STUDENT);
        return ResponseEntity.ok().body(students);
    }

    @GetMapping(path = "/users/students/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getStudentById(@PathVariable Long id) {
        User student = userService.getUser(id);
        return ResponseEntity.ok().body(student);
    }

    @PostMapping(path = "/users/students", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addStudent(@RequestBody StudentCreateDTO studentCreateDTO) {
        User registeredStudent = userService.registerUser(studentCreateDTO, UserService.UserType.STUDENT);
        return ResponseEntity.ok().body(registeredStudent);
    }

    @PutMapping(path = "/users/students/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO, @PathVariable Long id) {
        User updatedStudent = userService.updateUser(studentUpdateDTO, UserService.UserType.STUDENT);
        return ResponseEntity.ok().body(updatedStudent);
    }

    @DeleteMapping(path = "/users/students/{id:[0-9][0-9]*}")
    @ResponseBody
    public ResponseEntity deleteStudent( @PathVariable Long id) {
        userService.deleteUser(id, UserService.UserType.STUDENT);
        return ResponseEntity.ok().body("Deleted student successfully.");
    }

    @GetMapping(path = "/users/librarians", produces = "application/json")
    public ResponseEntity getLibrarians() {
        List<User> librarians = userService.getAll(UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body(librarians);
    }

    @GetMapping(path = "/users/librarians/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getLibrarianById(@PathVariable Long id) {
        User librarian = userService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }

    @PostMapping(path = "/users/librarians", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addLibrarian(@RequestBody UserCreateDTO userCreateDTO) {
        User registeredLibrarian = userService.registerUser(userCreateDTO, UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body(registeredLibrarian);
    }

    @PutMapping(path = "/users/librarians/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateLibrarian(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedLibrarian = userService.updateUser(userUpdateDTO, UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body(updatedLibrarian);
    }

    @DeleteMapping(path = "/users/librarians/{id:[0-9][0-9]*}")
    @ResponseBody
    public ResponseEntity deleteLibrarian( @PathVariable Long id) {
        userService.deleteUser(id, UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body("Deleted librarian successfully.");
    }

    @GetMapping(path = "/users/admins", produces = "application/json")
    public ResponseEntity getAdmins() {
        List<User> admins = userService.getAll(UserService.UserType.ADMIN);
        return ResponseEntity.ok().body(admins);
    }

    @GetMapping(path = "/users/admins/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getAdminById(@PathVariable Long id) {
        User librarian = userService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }

    @PostMapping(path = "/users/admins", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addAdmin(@RequestBody UserCreateDTO userCreateDTO) {
        User registeredAdmin = userService.registerUser(userCreateDTO, UserService.UserType.ADMIN);
        return ResponseEntity.ok().body(registeredAdmin);
    }

    @PutMapping(path = "/users/admins/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateAdmin(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedAdmin = userService.updateUser(userUpdateDTO, UserService.UserType.ADMIN);
        return ResponseEntity.ok().body(updatedAdmin);
    }

    @DeleteMapping(path = "/users/admins/{id:[0-9][0-9]*}")
    @ResponseBody
    public ResponseEntity deleteAdmin( @PathVariable Long id) {
        userService.deleteUser(id, UserService.UserType.ADMIN);
        return ResponseEntity.ok().body("Deleted admin successfully.");
    }

    /**
     * Get BookLoan services according to user
     */
//    @GetMapping(path = "/users/{username}/bookloans", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<List<BookLoan>> getBookLoans(@PathVariable String username) {
//        List<BookLoan> bookLoans = bookLoanService.getBookLoanByUser(username);
//        return ResponseEntity.ok().body(bookLoans);
//    }

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
