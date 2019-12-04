package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AdminRepository;
import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.dto.UserUpdateDTO;
import com.baylor.se.lms.dto.factory.AdminFactory;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.exception.UnmatchingPasswordException;
import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class AdminService implements IUserService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminFactory adminFactory;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public User registerUser(UserDTO userDTO)
    {
        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            throw new UnmatchingPasswordException("Password 1 and password 2 don't match with each other.");
        }
        log.info("Registering Admin: " + userDTO.getUsername());
        Admin admin = (Admin)adminFactory.getUser(userDTO);
        log.info("Saving admin : " + admin.getUsername());
        return adminRepository.save(admin);
    }

    @Override
    public User getUser(Long id){
        return adminRepository.findAdminById(id).orElseThrow(NotFoundException::new);
    }

    public List<Admin> getAll(){
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public User updateUser(UserUpdateDTO userUpdateDTO){
        Admin admin = (Admin) getUser(userUpdateDTO.getId());
        log.info("Updating Admin : " + admin.getUsername());
        admin.setUsername(userUpdateDTO.getUsername());
        admin.setEmail(userUpdateDTO.getEmail());
        admin.setName(userUpdateDTO.getName());
        admin.setPhoneNumber(userUpdateDTO.getPhoneNumber());
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

    public void changePassword(long id, String newPassword){
        Admin admin = adminRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Changing Password of: " + admin.getUsername());
        admin.setPassword(bcryptEncoder.encode(newPassword));
        adminRepository.save(admin);
    }
}
