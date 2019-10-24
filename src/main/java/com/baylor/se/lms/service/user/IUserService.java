package com.baylor.se.lms.service.user;

import com.baylor.se.lms.model.User;

public interface IUserService {
    public void registerUser(User user);
    public User getUser(Long id);
    public void updateUser(User user);
    public void deleteUser(Long id);
}
