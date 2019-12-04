package com.baylor.se.lms.dto.factory;

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
}
