package com.baylor.se.lms.service;

import com.baylor.se.lms.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Interface for User Service
 */
public interface IUserService {
    User registerUser(User user);
    User getUser(Long id);
    List<User> getAll();
    User updateUser(User user, Long id);
    void deleteUser(Long id);

   default User updateValuesUser(User updated, User oldUser){
        oldUser.setEmail(updated.getEmail());
        oldUser.setName(updated.getName());
        oldUser.setUsername(updated.getUsername());
        oldUser.setPhoneNumber(updated.getPhoneNumber());
        return oldUser;
   }
   default User convertAfterDelete(User user){
       String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
       user.setUsername(user.getUsername()+timeStamp);
       user.setPhoneNumber(String.format ("%010d", user.getId()));
       user.setEmail("deleted"+user.getId()+"@gmail.com");
       return user;
   }

}
