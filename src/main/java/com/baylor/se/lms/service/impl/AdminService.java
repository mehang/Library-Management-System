package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AdminRepository;
import com.baylor.se.lms.dto.factory.AdminFactory;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @Override
    public User getUser(Long id){
        log.info("Getting admin id : "+ id);
        return adminRepository.findAdminById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<User> getAll()
    {
        List<Admin> allAdmins =adminRepository.findAllByDeleteFlagFalse();
        List<User> admins = new ArrayList<>(allAdmins);
        return admins;
    }

    @Override
    public User updateUser(User user, Long id){
        Admin admin = (Admin)getUser(id);
        admin.setEmail(user.getEmail());
        admin.setName(user.getName());
        admin.setUsername(user.getUsername());
        admin.setPhoneNumber(user.getPhoneNumber());
        return adminRepository.save(admin);
    }

    @Override
    public void deleteUser(Long id){
        Admin admin =   adminRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Deleting Admin: " + admin.getUsername());
        admin.setDeleteFlag(true);
        adminRepository.save(admin);
        jmsTemplate.convertAndSend("post-admin-delete", admin);
    }

    @JmsListener(destination = "post-admin-delete", containerFactory = "postDeleteFactory")
    public void postDelete(Admin admin) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        admin.setUsername(admin.getUsername()+timeStamp);
        admin.setPhoneNumber(String.format ("%010d", admin.getId()));
        admin.setEmail("deleted"+admin.getId()+"@gmail.com");
        adminRepository.save(admin);
    }

}
