package com.baylor.se.lms.service.user.impl;

import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.data.StudentRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements IUserService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public void registerUser(User user){
        studentRepository.save((Student) user);
    }

    @Override
    public User getUser(Long id){
        Student librarian =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return librarian;
    }

    @Override
    public void updateUser(User user){studentRepository.save((Student) user);};

    @Override
    public void deleteUser(Long id){
        Student student =   studentRepository.findById(id).orElseThrow(NotFoundException::new);
        student.setDeleteFlag(true);
        studentRepository.save(student);
    }
}
