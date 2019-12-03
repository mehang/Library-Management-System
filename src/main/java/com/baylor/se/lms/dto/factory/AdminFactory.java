package com.baylor.se.lms.dto.factory;

import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class AdminFactory extends UserFactory{
    @Override
    public User getUser() {
        return new Admin();
    }
    @Override
    public Role getRole(){
        Role  role = new Role();
        role.setRole("ADMIN");
        return role;
    }
}
