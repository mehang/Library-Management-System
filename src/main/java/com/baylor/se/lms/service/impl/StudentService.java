package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.StudentRepository;
import com.baylor.se.lms.exception.NotFoundException;
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
public class StudentService implements IUserService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public User registerUser(User user){
        log.info("Registering Student: "+ user.getUsername());
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setRole("STUDENT");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        log.info("Saving student : " + user.getUsername());
       return studentRepository.save((Student) user);
    }

    @Override
    public User getUser(Long id){

        log.info("Get student by id: " +id);
        Student student =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return student;
    }

    public List<Student> getAll(){
        log.info("Get all students");
        List<Student> students =(List<Student>) studentRepository.findAll();
        return students;
    }

    @Override
    public User updateUser(User user){
        log.info("Update student: " + user.getUsername());
        return studentRepository.save((Student) user);
    }

    @Override
    public void deleteUser(Long id){
        log.info("Deleting student id: "+ id);
        Student student =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        student.setDeleteFlag(true);
        studentRepository.save(student);
    }

    public void changePassword(long id, String newPassword){
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Changing Password: " +  student.getUsername());
        student.setPassword(bcryptEncoder.encode(newPassword));
        studentRepository.save(student);
    }
}
