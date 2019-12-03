package com.baylor.se.lms.dto.converter;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.User;

public class AdminDTOConverter extends UserDTOConverter{
    @Override
    public User convertingTo() {
        return new Admin();
    }
}
