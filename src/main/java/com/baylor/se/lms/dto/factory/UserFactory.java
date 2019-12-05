package com.baylor.se.lms.dto.factory;

import com.baylor.se.lms.dto.user.create.UserCreateDTO;
import com.baylor.se.lms.dto.user.update.UserUpdateDTO;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public abstract class UserFactory {

    public User getUser(UserCreateDTO userCreateDTO){
        User user = createUser();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword1());
        user.setEmail(userCreateDTO.getEmail());
        user.setName(userCreateDTO.getName());
        user.setPhoneNumber(userCreateDTO.getPhoneNumber());
        Role role = getRole();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return user;
    }

    public User getUpdatingUser(UserUpdateDTO userUpdateDTO){
        User user = createUser();
        user.setId(userUpdateDTO.getId());
        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());
        user.setName(userUpdateDTO.getName());
        user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        return user;
    }

    public abstract User createUser();
    public abstract Role getRole();
}
