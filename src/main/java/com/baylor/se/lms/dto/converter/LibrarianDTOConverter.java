package com.baylor.se.lms.dto.converter;

import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.model.User;

public class LibrarianDTOConverter extends UserDTOConverter{
    @Override
    public User getUser() {
        return new Librarian();
    }
}