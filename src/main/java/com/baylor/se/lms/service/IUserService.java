package com.baylor.se.lms.service;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.model.User;

import java.util.List;

public interface IUserService {
    public User registerUser(UserDTO userDTO);
    public User getUser(Long id);
    public User updateUser(User user);
    public void deleteUser(Long id);
}
