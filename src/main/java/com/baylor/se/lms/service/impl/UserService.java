package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.UserRepository;
import com.baylor.se.lms.model.CustomUserDetails;
import com.baylor.se.lms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

//    public User getUserByUsername(String username){
//        return userRepository.findByUsername(username);
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUsers = userRepository.findByUsername(username);
//
//        optionalUsers
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        return optionalUsers
//                .map(CustomUserDetails::new).get();
//    }

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

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
