package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AuthorRepository;
import com.baylor.se.lms.data.BookCategoryRepository;
import com.baylor.se.lms.data.LibrarianRepository;
import com.baylor.se.lms.dto.BookDTO;
import com.baylor.se.lms.model.Book;
import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.BookCategory;
import com.baylor.se.lms.model.BookSpecification;
import com.baylor.se.lms.model.Librarian;
import com.baylor.se.lms.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService implements IBookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibrarianRepository librarianRepo;
    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    BookCategoryRepository bookCategoryRepo;

    @Autowired
    BookSpecificationService bookSpecificationService;

    @Autowired
    LibrarianService librarianService;
    @Override
    public Book registerBook(BookDTO book){

        BookSpecification bookSpecification =  new BookSpecification();
        bookSpecification.setName(book.getName());
        bookSpecification.setPublication(book.getPublication());
        bookSpecification.setIsbn(book.getIsbn());
        bookSpecification.setLanguage(book.getLanguage());
        bookSpecification.setAuthor(authorRepo.findAuthorById(book.getAuthorId()).orElseThrow(NotFoundException::new));
        Set<BookCategory> bookCategorySet =  new HashSet<>();
        book.getBookCategory().forEach( s -> bookCategorySet.add(bookCategoryRepo.findById(s).orElseThrow(NotFoundException::new)));
        bookSpecification.setBookCategorySet(bookCategorySet);
        bookSpecification = bookSpecificationService.saveBookSpec(bookSpecification);
        Book newBook =  new Book();
        String serialNumber= book.getIsbn().concat("Book1");
        newBook.setSerialNo(serialNumber);
        newBook.setSpecification(bookSpecification);
        newBook.setUpdatedBy((Librarian) librarianService.getUser(book.getLibrarianId()));
        return bookRepository.save(newBook);
    }

    @Override
    public Book getBook(Long id){
        Book book =  bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return book;
    }

    @Override
    public List<Book> getBooks(){
        List<Book> books = (List<Book>) bookRepository.findAll();
        books.removeIf(Book::isDeleteFlag);
        return books;
    }

    @Override
    public void updateBook(Book book){
        bookRepository.save(book);
    }



    public Book increaseBook(String isbn, long librarianId){

        List<Book> bookList = bookRepository.findBooksBySerialNoContaining(isbn);
        int bookCounter = bookList.size() + 1;
        Book newBook =  new Book();
        String newSerialNumber = generateNewSerial(isbn,bookCounter);
        newBook.setSerialNo(newSerialNumber);
        newBook.setStatus(Book.BookStatus.AVAILABLE);
        Librarian librarian = (Librarian) librarianService.getUser(librarianId);
        newBook.setUpdatedBy(librarian);
        newBook.setSpecification(bookList.get(0).getSpecification());
        bookRepository.save(newBook);
        return  newBook;
    }
    private String generateNewSerial(String isbn, int counter){
        String serialNumber = isbn.concat("Book");
        return  serialNumber.concat(String.valueOf(counter));

    }
}
