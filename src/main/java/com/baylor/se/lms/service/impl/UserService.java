package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.*;
import com.baylor.se.lms.model.CustomUserDetails;
import com.baylor.se.lms.model.PasswordResetToken;
import com.baylor.se.lms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @Autowired
    LibrarianService librarianService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User userObj = user.get();
        return new org.springframework.security.core.userdetails.User(userObj.getUsername(), userObj.getPassword(), getAuthority(userObj));
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {return userRepository.findByEmail(email);}

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public PasswordResetToken createPasswordResetToken(User user){
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        return resetTokenRepository.save(token);
    }

    @Transactional
    public void resetPassword(String resetToken, String newPassword){
              PasswordResetToken passwordResetToken = resetTokenRepository.findByToken(resetToken);
              User user = passwordResetToken.getUser();
              if (user.getDiscriminatorValue().equals("ADMIN")){
                  adminService.changePassword(user.getId(), newPassword);
              } else if (user.getDiscriminatorValue().equals("STUDENT")){
                  studentService.changePassword(user.getId(), newPassword);
              } else {
                  librarianService.changePassword(user.getId(),newPassword);
              }
              resetTokenRepository.delete(passwordResetToken);
    }

    public Boolean isResetTokenValid(String resetToken){
        PasswordResetToken passwordResetToken = resetTokenRepository.findByToken(resetToken);
        return (passwordResetToken != null);
    }
}
