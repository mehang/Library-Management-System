package com.baylor.se.lms.dto.converter;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.User;

public abstract class UserDTOConverter {
    public abstract User getUser();

    public User convert(UserDTO userDTO){
        User user = getUser();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword1());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
