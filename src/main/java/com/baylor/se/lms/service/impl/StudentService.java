package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.StudentRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.Student;
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
 * Student service performs all CRUD operation for student entity.
 * Implements IUserService interface
 */

@Service
@Slf4j
public class StudentService implements IUserService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * Save student.
     * @param user : Student
     * @return Saved user record
     */
    @Override
    public User registerUser(User user){
       return studentRepository.save((Student)user);
    }

    /**
     * Return  Student of provide id.
     * @param id : Student id
     * @return Student records
     */
    @Override
    public User getUser(Long id){
        log.info("Get student by id: " +id);
        Student student =   studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not find"));
        return student;
    }

    /**
     * Return All non-deleted students
     * @return List of students
     */
    @Override
    public List<User> getAll()
    {
        log.info("Fetching all students");
        List<Student> allStudents =studentRepository.findAllByDeleteFlagFalse();
        List<User> students = new ArrayList<>(allStudents);
        return students;
    }

    /**
     * Update Student of given id
     * @param user : New Update User details
     * @param id : Student id
     * @return Updated student
     */
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

    /**
     * Soft deletes student with giving id.
     * @param id : Student Id
     */
    @Override
    public void deleteUser(Long id){
        log.info("Deleting student id: "+ id);
        Student student =   studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        student.setDeleteFlag(true);
        studentRepository.save(student);
        jmsTemplate.convertAndSend("post-student-delete", student);
    }

    /**
     * Converts a student information after delete to preserve uniques.
     * @param student : Deleted Student
     */
    @JmsListener(destination = "post-student-delete", containerFactory = "postDeleteFactory")
    public void postDelete (Student student) {
        student = (Student) convertAfterDelete(student);
        studentRepository.save(student);
    }

}
