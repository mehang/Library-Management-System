package com.baylor.se.lms.dto.factory;

import com.baylor.se.lms.dto.user.create.StudentCreateDTO;
import com.baylor.se.lms.dto.user.create.UserCreateDTO;
import com.baylor.se.lms.dto.user.update.StudentUpdateDTO;
import com.baylor.se.lms.dto.user.update.UserUpdateDTO;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class StudentFactory extends UserFactory{
    @Override
    public User createUser() {
        return new Student();
    }

    @Override
    public Role getRole(){
        Role  role = new Role();
        role.setRole("STUDENT");
        return role;
    }

    @Override
    public User getUser(UserCreateDTO userCreateDTO) {
        StudentCreateDTO studentCreateDTO = (StudentCreateDTO) userCreateDTO;
        Student student =  (Student)super.getUser(userCreateDTO);
        student.setDegree(studentCreateDTO.getDegree());
        return student;
    }

    @Override
    public User getUpdatingUser(UserUpdateDTO userUpdateDTO) {
        StudentUpdateDTO studentUpdateDTO = (StudentUpdateDTO) userUpdateDTO;
        Student student =  (Student)super.getUpdatingUser(userUpdateDTO);
        student.setDegree(studentUpdateDTO.getDegree());
        return student;
    }
}
