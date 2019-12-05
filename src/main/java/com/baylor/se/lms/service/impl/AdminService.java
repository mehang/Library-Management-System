package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AdminRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  Admin service handles all CRUD for Admins.
 */

@Service
@Slf4j
public class AdminService implements IUserService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public User registerUser(User user)
    {
        return adminRepository.save((Admin)user);
    }

    /**
     * Returns Admin with provided id
     * @param id
     * @return User
     */
    @Override
    public User getUser(Long id){
        log.info("Getting admin id : "+ id);
        return adminRepository.findAdminById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Returns All Admins
     * @return
     */
    @Override
    public List<User> getAll()
    {
        log.info("Get all admins");
        List<Admin> allAdmins =adminRepository.findAllByDeleteFlagFalse();
        List<User> admins = new ArrayList<>(allAdmins);
        return admins;
    }

    /**
     * Updates admin of given  id
     * @param user
     * @param id
     * @return Admin
     */
    @Override
    public User updateUser(User user, Long id){
        Admin admin = (Admin)getUser(id);
        admin = (Admin) updateValuesUser(user,admin);
        return adminRepository.save(admin);
    }

    /**
     * Deletes admin of id. In our application, delete  means soft delete where we set the deletedFlag to true
     * @param id
     */
    @Override
    public void deleteUser(Long id){
        Admin admin =   adminRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Deleting Admin: " + admin.getUsername());
        admin.setDeleteFlag(true);
        adminRepository.save(admin);
        jmsTemplate.convertAndSend("post-admin-delete", admin);
    }

    /**
     * Adds timestamp for deleted  admin to make the username available after delete.
     * @param admin
     */
    @JmsListener(destination = "post-admin-delete", containerFactory = "postDeleteFactory")
    public void postDelete(Admin admin) {
        admin = (Admin) convertAfterDelete(admin);
        adminRepository.save(admin);
    }

}
