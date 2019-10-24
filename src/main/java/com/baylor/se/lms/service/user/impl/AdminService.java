package com.baylor.se.lms.service.user.impl;

import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.AdminRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IUserService {
    @Autowired
    AdminRepository adminRepository;

    @Override
    public void registerUser(User user)
    {
        adminRepository.save((Admin)user);
    }

    @Override
    public User getUser(Long id){
        Admin admin =   adminRepository.findById(id).orElseThrow(NotFoundException::new);
        return admin;
    }

    @Override
    public void updateUser(User user){
        adminRepository.save((Admin) user);
    }

    @Override
    public void deleteUser(Long id){
        Admin admin =   adminRepository.findById(id).orElseThrow(NotFoundException::new);
        admin.setDeleteFlag(true);
        adminRepository.save(admin);
    }
}
