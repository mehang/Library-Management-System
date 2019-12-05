package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.*;
import com.baylor.se.lms.dto.PasswordChangeDTO;
import com.baylor.se.lms.dto.PasswordForgotDTO;
import com.baylor.se.lms.dto.PasswordResetDTO;
import com.baylor.se.lms.dto.user.create.UserCreateDTO;
import com.baylor.se.lms.dto.user.update.UserUpdateDTO;
import com.baylor.se.lms.dto.factory.AdminFactory;
import com.baylor.se.lms.dto.factory.StudentFactory;
import com.baylor.se.lms.exception.InvalidEmailException;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.exception.UnmatchingPasswordException;
import com.baylor.se.lms.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.baylor.se.lms.dto.factory.LibrarianFactory;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    public enum UserType {
        ADMIN,
        LIBRARIAN,
        STUDENT
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    StudentFactory studentFactory;

    @Autowired
    LibrarianFactory librarianFactory;

    @Autowired
    AdminFactory adminFactory;

    @Autowired
    StudentService studentService;

    @Autowired
    LibrarianService librarianService;

    @Autowired
    AdminService adminService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Get user by username " + username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User userObj = user.get();
        return new org.springframework.security.core.userdetails.User(userObj.getUsername(), userObj.getPassword(), getAuthority(userObj));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        log.info("Role Authority of " + user.getUsername());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public void createPasswordResetToken(PasswordForgotDTO passwordForgotDTO) {
        Optional<User> optionalUser = findByEmail(passwordForgotDTO.getEmail());
        if (optionalUser.isEmpty()){
            throw new InvalidEmailException("Invalid email address.");
        }
        User user = optionalUser.get();
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        PasswordResetToken passwordResetToken = resetTokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("mehang.rai007@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", passwordResetToken);
        model.put("user", user);
        model.put("signature", "http://lms.com");
        model.put("resetUrl", "http://localhost:3000" + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail);
    }

    private void changePassword(long id, String newPassword) {
        User user = userRepository.findUserById(id).orElseThrow(NotFoundException::new);
        log.info("Change Password: " + user.getUsername());
        user.setPassword(bcryptEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void changePassword(PasswordChangeDTO passwordChangeDTO){
        if (!passwordChangeDTO.getPassword1().equals(passwordChangeDTO.getPassword2())) {
            throw new UnmatchingPasswordException("Password 1 and password 2 does not match with each other.");
        }
        changePassword(passwordChangeDTO.getId(), passwordChangeDTO.getPassword1());
    }

    @Transactional
    public void resetPassword(PasswordResetDTO passwordResetDTO) {
        if (!passwordResetDTO.getPassword1().equals(passwordResetDTO.getPassword2())) {
            throw new UnmatchingPasswordException("Password 1 and password 2 does not match with each other.");
        }
        PasswordResetToken passwordResetToken = resetTokenRepository.findByToken(passwordResetDTO.getResetToken());
        User user = passwordResetToken.getUser();
        changePassword(user.getId(), passwordResetDTO.getPassword1());
        resetTokenRepository.delete(passwordResetToken);
    }

    public User registerUser(UserCreateDTO userCreateDTO, UserType userType) {
        if (!userCreateDTO.getPassword1().equals(userCreateDTO.getPassword2())) {
            throw new UnmatchingPasswordException("Password 1 and password 2 don't match with each other.");
        }
        User user;
        log.info("Registering: " + userCreateDTO.getUsername());
        if (userType == UserType.ADMIN) {
            user = adminFactory.getUser(userCreateDTO);
            return adminService.registerUser((Admin) user);
        } else if (userType == UserType.LIBRARIAN) {
            user = librarianFactory.getUser(userCreateDTO);
            return librarianService.registerUser((Librarian) user);
        } else {
            user = studentFactory.getUser(userCreateDTO);
            return adminService.registerUser((Student) user);
        }
    }

    public User getUser(Long id) {
        log.info("Get student by id: " + id);
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }


    public List<User> getAll(UserType userType) {
        if (userType == UserType.ADMIN) {
            log.info("Get all admins");
            return adminService.getAll();
        } else if (userType == UserType.LIBRARIAN) {
            log.info("Get all librarians");
            return librarianService.getAll();
        } else {
            log.info("Get all students");
            return studentService.getAll();
        }
    }

    public User updateUser(UserUpdateDTO userUpdateDTO, UserType userType) {
        User updatingUser;
        log.info("Updating : " + userUpdateDTO.getUsername());
        if (userType == UserType.ADMIN) {
            updatingUser = adminFactory.getUpdatingUser(userUpdateDTO);
            return adminService.updateUser(updatingUser, updatingUser.getId());
        } else if (userType == UserType.LIBRARIAN) {
            updatingUser = librarianFactory.getUpdatingUser(userUpdateDTO);
            return librarianService.updateUser(updatingUser, updatingUser.getId());
        } else {
            updatingUser = studentFactory.getUpdatingUser(userUpdateDTO);
            return studentService.updateUser(updatingUser, updatingUser.getId());
        }
    }

    @JmsListener(destination = "post-user-delete", containerFactory = "postDeleteFactory")
    public void deleteUser(Long id, UserType userType) {
        if (userType == UserType.ADMIN) {
             adminService.deleteUser( id);
        } else if (userType == UserType.LIBRARIAN) {
             librarianService.deleteUser(id);
        } else {
            studentService.deleteUser(id);
        }
    }

}
