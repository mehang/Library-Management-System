package com.baylor.se.lms.dto.factory;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public abstract class UserFactory {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUser(UserDTO userDTO){
        User user = createUser();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword1()));
        user.setEmail(userDTO.getEmail());
        //user.setPassword(userDTO.getPassword1());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        Role role = getRole();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return user;
    }

    public abstract User createUser();
    public abstract Role getRole();
}
