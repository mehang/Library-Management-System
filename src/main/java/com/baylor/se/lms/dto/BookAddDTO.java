package com.baylor.se.lms.dto;

/**
 * Book Addition DTO. Used for increasing the quantity of  a specified book.
 */
public class BookAddDTO {
    private String isbn;
    //private User Librarian;
    private Long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
