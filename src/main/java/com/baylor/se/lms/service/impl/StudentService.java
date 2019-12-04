package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.dto.UserDTO;
import com.baylor.se.lms.dto.factory.StudentFactory;
import com.baylor.se.lms.exception.UnmatchingPasswordException;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.StudentRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class StudentService implements IUserService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentFactory studentFactory;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public User registerUser(UserDTO userDTO){
        if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
            throw new UnmatchingPasswordException("Password 1 and password 2 don't match with each other.");
        }
        log.info("Registering Student: "+ userDTO.getUsername());
        Student student= (Student) studentFactory.getUser(userDTO);
        log.info("Saving student : " + student.getUsername());
       return studentRepository.save(student);
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
        jmsTemplate.convertAndSend("post-student-delete", student);
    }

    @JmsListener(destination = "post-student-delete", containerFactory = "postDeleteFactory")
    public void postDelete(Student student) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        student.setUsername(student.getUsername()+timeStamp);
        student.setPhoneNumber(String.format ("%010d", student.getId()));
        student.setEmail("deleted"+student.getId()+"@gmail.com");
        studentRepository.save(student);
    }

    public void changePassword(long id, String newPassword){
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        log.info("Changing Password: " +  student.getUsername());
        student.setPassword(bcryptEncoder.encode(newPassword));
        studentRepository.save(student);
    }
}
