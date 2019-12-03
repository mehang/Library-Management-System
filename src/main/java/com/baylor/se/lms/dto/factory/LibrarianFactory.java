package com.baylor.se.lms.dto.factory;

import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.Role;
import com.baylor.se.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class LibrarianFactory extends UserFactory{
    @Override
    public User getUser() {
        return new Librarian();
    }

    @Override
    public Role getRole(){
        Role  role = new Role();
        role.setRole("LIBRARIAN");
        return role;
    }
}