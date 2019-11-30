package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AdminRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class AdminService implements IUserService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


    @Override
    public User registerUser(User user)
    {
        log.info("Registering Admin: " + user.getUsername());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = new Role();
        log.info("Setting Role to Admin");
        role.setRole("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return adminRepository.save((Admin)user);
    }

    @Override
    public User getUser(Long id){
        Admin admin =   adminRepository.findAdminById(id).orElseThrow(NotFoundException::new);
        return admin;
    }

    public List<Admin> getAll(){
        List<Admin> admins = (List<Admin>) adminRepository.findAll();
        return admins;
    }

    @Override
    public User updateUser(User user){
        log.info("Updating Admin : " + user.getUsername());
        return adminRepository.save((Admin) user);
    }

    @Override
    public void deleteUser(Long id){
        Admin admin =   adminRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Deleting Admin: " + admin.getUsername());
        admin.setDeleteFlag(true);
        adminRepository.save(admin);
    }

    public void changePassword(long id, String newPassword){
        Admin admin = adminRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Changing Password of: " + admin.getUsername());
        admin.setPassword(bcryptEncoder.encode(newPassword));
        adminRepository.save(admin);
    }
}
