package com.baylor.se.lms.service;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.dto.UserUpdateDTO;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface IUserService {
    public User registerUser(UserDTO userDTO);
    public User getUser(Long id);
    public User updateUser(UserUpdateDTO userUpdateDTO);
    public void deleteUser(Long id);

}
