package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.StudentRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IUserService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public User registerUser(User user){
       return studentRepository.save((Student) user);
    }

    @Override
    public User getUser(Long id){
        Student student =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return student;
    }

    public List<Student> getAll(){
        List<Student> students =(List<Student>) studentRepository.findAll();
        return students;
    }

    @Override
    public User updateUser(User user){ return studentRepository.save((Student) user);};

    @Override
    public void deleteUser(Long id){
        Student student =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        student.setDeleteFlag(true);
        studentRepository.save(student);
    }
}
