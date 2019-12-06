package com.baylor.se.lms.controller;

import com.baylor.se.lms.data.PasswordResetTokenRepository;
import com.baylor.se.lms.dto.UserVerifyDTO;
import com.baylor.se.lms.dto.LoginDTO;
import com.baylor.se.lms.dto.PasswordChangeDTO;
import com.baylor.se.lms.dto.PasswordForgotDTO;
import com.baylor.se.lms.dto.PasswordResetDTO;
import com.baylor.se.lms.dto.user.create.StudentCreateDTO;
import com.baylor.se.lms.dto.user.create.UserCreateDTO;
import com.baylor.se.lms.dto.user.update.StudentUpdateDTO;
import com.baylor.se.lms.dto.user.update.UserUpdateDTO;
import com.baylor.se.lms.model.BookLoan;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.security.TokenProvider;
import com.baylor.se.lms.service.impl.BookLoanService;
import com.baylor.se.lms.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User Controller  handles all the functions for CRUD . Additionally, it also handles user login, password reset and token management. It
 */
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

    /**
     *  Handles user authentication
     * @param loginUser : contains username and password
     * @return Authorization model
     *
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity register(@RequestBody LoginDTO loginUser) {
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

    /**
     * Handles change password request
     * @param passwordChangeDTO : contains two passwords
     * @return Successful response
     */
    @PostMapping(path = "/users/change-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO);
        return ResponseEntity.ok().body("Password has been changed successfully.");
    }

    /**
     * Creates password reset token and sends emaik
     * @param passwordForgotDTO : passwordForget
     * @return Response message
     */
    @PostMapping(path = "/users/forgot-password", consumes = "application/json")
    public ResponseEntity sendResetLink(@RequestBody PasswordForgotDTO passwordForgotDTO) {
        userService.createPasswordResetToken(passwordForgotDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password reset link sent to the email address.");
    }

    /**
     *  handles reset password
     * @param passwordResetDTO :  Contain token, password1 and password2
     * @return JSON Message with success message
     */
    @PostMapping(path="/users/reset-password", consumes = "application/json")
    public ResponseEntity resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        userService.resetPassword(passwordResetDTO);
        return ResponseEntity.ok().body("Password has been reset.");
    }

    //    @PreAuthorize("hasAnyRole('STUDENT','ROLE_STUDENT')")

    /**
     * Handles GET all students
     * @return List all students in JSON
     */
    @GetMapping(path = "/users/students", produces = "application/json")
    public ResponseEntity getStudents() {
        List<User> students = userService.getAll(UserService.UserType.STUDENT);
        return ResponseEntity.ok().body(students);
    }

    /**
     * GET specific student.
     * @param id : Student ID
     * @return JSON response
     */
    @GetMapping(path = "/users/students/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getStudentById(@PathVariable Long id) {
        User student = userService.getUser(id);
        return ResponseEntity.ok().body(student);
    }

    /**
     * Handles POST request create student.
     * @param studentCreateDTO : Student create details
     * @return JSON response
     */
    @PostMapping(path = "/users/students", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addStudent(@RequestBody StudentCreateDTO studentCreateDTO) {
        User registeredStudent = userService.registerUser(studentCreateDTO, UserService.UserType.STUDENT);
        return ResponseEntity.ok().body(registeredStudent);
    }

    /**
     * Handle PUT request to update student.
     * @param studentUpdateDTO contains student information to be updated
     * @param id : student id
     * @return JSON response with updated student
     */
    @PutMapping(path = "/users/students/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO, @PathVariable Long id) {
        User updatedStudent = userService.updateUser(studentUpdateDTO, UserService.UserType.STUDENT);
        return ResponseEntity.ok().body(updatedStudent);
    }

    /**
     * Handle Delete student
     * @param id:
     * @return Successful delete message
     */
    @DeleteMapping(path = "/users/students/{id:[0-9][0-9]*}")
    @ResponseBody
    public ResponseEntity deleteStudent( @PathVariable Long id) {
        userService.deleteUser(id, UserService.UserType.STUDENT);
        return ResponseEntity.ok().body("Deleted student successfully.");
    }

    /**
     * Handle GET all librarians request
     * @return Librarians List as JSON response
     */
    @GetMapping(path = "/users/librarians", produces = "application/json")
    public ResponseEntity getLibrarians() {
        List<User> librarians = userService.getAll(UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body(librarians);
    }

    /**
     * Handle GET specific librarians.
     * @param id : Librarian ID
     * @return JSON response with librarian object
     */
    @GetMapping(path = "/users/librarians/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getLibrarianById(@PathVariable Long id) {
        User librarian = userService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }

    /**
     * Handle POST  request to create librarian. It consumes JSON with User structure.
     * @param userCreateDTO : Librarian Details
     * @return Created Librarian as JSON response
     */
    @PostMapping(path = "/users/librarians", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addLibrarian(@RequestBody UserCreateDTO userCreateDTO) {
        User registeredLibrarian = userService.registerUser(userCreateDTO, UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body(registeredLibrarian);
    }

    /**
     * Handle PUT request to update librarians.
     * @param userUpdateDTO :  Librarian update details
     * @param id: librarian id
     * @return JSON response with updated librarian
     */
    @PutMapping(path = "/users/librarians/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateLibrarian(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedLibrarian = userService.updateUser(userUpdateDTO, UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body(updatedLibrarian);
    }

    /**
     * Handle DELETE request of librarians
     * @param id : Librarian id
     * @return JSON response with successful message
     */
    @DeleteMapping(path = "/users/librarians/{id:[0-9][0-9]*}")
    @ResponseBody
    public ResponseEntity deleteLibrarian( @PathVariable Long id) {
        userService.deleteUser(id, UserService.UserType.LIBRARIAN);
        return ResponseEntity.ok().body("Deleted librarian successfully.");
    }

    /**
     * Handle GET request to fetch admins.
     * @return JSON response with all admins
     */
    @GetMapping(path = "/users/admins", produces = "application/json")
    public ResponseEntity getAdmins() {
        List<User> admins = userService.getAll(UserService.UserType.ADMIN);
        return ResponseEntity.ok().body(admins);
    }

    /**
     * Handle GET request  for specific admins
     * @param id : Admin Id
     * @return JSON response with all admin
     */
    @GetMapping(path = "/users/admins/{id:[0-9][0-9]*}", produces = "application/json")
    public ResponseEntity getAdminById(@PathVariable Long id) {
        User librarian = userService.getUser(id);
        return ResponseEntity.ok().body(librarian);
    }

    /**
     * Handle POST request for admin creation. Consumes JSON stucture of admin detials.
     * @param userCreateDTO : Admin Details
     * @return Saved admin as JSON response
     */
    @PostMapping(path = "/users/admins", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity addAdmin(@RequestBody UserCreateDTO userCreateDTO) {
        User registeredAdmin = userService.registerUser(userCreateDTO, UserService.UserType.ADMIN);
        return ResponseEntity.ok().body(registeredAdmin);
    }

    /**
     * Handle PUT request to update admin.
     * @param userUpdateDTO : Update admin details
     * @param id : id of admin to be updated
     * @return Updated admin as JSON Response
     */
    @PutMapping(path = "/users/admins/{id:[0-9][0-9]*}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity updateAdmin(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        User updatedAdmin = userService.updateUser(userUpdateDTO, UserService.UserType.ADMIN);
        return ResponseEntity.ok().body(updatedAdmin);
    }

    /**
     * Handle DELETE request of admin
     * @param id :  Admin id
     * @return Successful admin delete message
     */
    @DeleteMapping(path = "/users/admins/{id:[0-9][0-9]*}")
    @ResponseBody
    public ResponseEntity deleteAdmin( @PathVariable Long id) {
        userService.deleteUser(id, UserService.UserType.ADMIN);
        return ResponseEntity.ok().body("Deleted admin successfully.");
    }

    /**
     * Get BookLoan services according to user
     * @param username username to get bookloans
     * @return All book loan records
     */
    @GetMapping(path = "/users/{username}/bookloans", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BookLoan>> getBookLoans(@PathVariable String username) {
        List<BookLoan> bookLoans = bookLoanService.getBookLoanByUser(username);
        return ResponseEntity.ok().body(bookLoans);
    }
    /**
     * Get BookLoan services according to user
     * @param username username to get active bookloans
     * @return All active book loan records
     */
    @GetMapping(path = "/users/{username}/activeloans", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<BookLoan>> getActiveBookLoans(@PathVariable String username) {
        List<BookLoan> bookLoans = bookLoanService.getOnlyActiveRequest(username);
        return ResponseEntity.ok().body(bookLoans);
    }

    /**
     * This is mock verification api that verifies student. Currently uses text file to verify student.
     * @param id :  Student id
     * @return Verification message
     *
     */

    @GetMapping(path = "/users/students/verify/{id:[0-9][0-9]*}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<UserVerifyDTO> verifyStudent(@PathVariable Long id) {

        UserVerifyDTO verifyDTO = new UserVerifyDTO();
        verifyDTO.setUserId(id);
        verifyDTO.setVerified(false);
        BufferedReader reader = null;
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
            reader.close();
        } catch (IOException e) {
            verifyDTO.setVerified(false);
        }
        return ResponseEntity.ok().body(verifyDTO);
    }
}
