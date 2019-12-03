package com.baylor.se.lms.dto.converter;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.User;

public abstract class UserDTOConverter {
    private User user;

    public abstract User convertingTo();

    public User convert(UserDTO userDTO){
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword1());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
