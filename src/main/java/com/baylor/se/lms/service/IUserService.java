package com.baylor.se.lms.service;

import com.baylor.se.lms.model.User;

import java.util.List;

public interface IUserService {
    public User registerUser(User user);
    public User getUser(Long id);
    public List<User> getAll();
    public User updateUser(User user, Long id);
    public void deleteUser(Long id);
}
