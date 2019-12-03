package com.baylor.se.lms.dto.converter;

import com.baylor.se.lms.model.Student;
import com.baylor.se.lms.model.User;

public class StudentDTOConverter extends UserDTOConverter{
    @Override
    public User convertingTo() {
        return new Student();
    }
}
