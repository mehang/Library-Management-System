package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.StudentRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
public class StudentService implements IUserService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public User registerUser(User user){
       return studentRepository.save((Student)user);
    }

    @Override
    public User getUser(Long id){
        log.info("Get student by id: " +id);
        Student student =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return student;
    }

    @Override
    public List<User> getAll()
    {
        List<Student> allStudents =studentRepository.findAllByDeleteFlagFalse();
        List<User> students = new ArrayList<>(allStudents);
        return students;
    }

    @Override
    public User updateUser(User user, Long id){
        Student updatingStudent = (Student) user;
        Student student = (Student) getUser(id);
        log.info("Update student: " + student.getUsername());
        student.setUsername(updatingStudent.getUsername());
        student.setEmail(updatingStudent.getEmail());
        student.setName(updatingStudent.getName());
        student.setPhoneNumber(updatingStudent.getPhoneNumber());
        student.setDegree(updatingStudent.getDegree());
        return studentRepository.save(student);
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

}
