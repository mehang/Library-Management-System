package com.baylor.se.lms.service.impl;

import com.baylor.se.lms.data.AuthorRepository;
import com.baylor.se.lms.data.BookCategoryRepository;
import com.baylor.se.lms.data.BookLoanRepository;
import com.baylor.se.lms.data.BookRepository;
import com.baylor.se.lms.dto.*;
import com.baylor.se.lms.exception.BadRequestException;
import com.baylor.se.lms.exception.NotFoundException;
import com.baylor.se.lms.model.*;
import com.baylor.se.lms.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Book Service handles all CRUD operations of Book. Additionally, it also handle request
 * for Book Request, Issue and Return. It also creates BookLoan and BookLog records.
 * Implements IBookService interface
 */
@Service
@Slf4j
public class BookService implements IBookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    BookCategoryRepository bookCategoryRepo;

    @Autowired
    BookSpecificationService bookSpecificationService;

    @Autowired
    LibrarianService librarianService;

    @Autowired
    StudentService studentService;

    @Autowired
    BookLoanRepository bookLoanRepository;

    /**
     *  Creates new book with new specification.
     *  This function adds new book along with the specification.
     * @param book:  Book details
     * @return Book: Created book
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Book registerBook(BookDTO book){
        log.info("Creating new  Book ");
        BookSpecification bookSpecification =  new BookSpecification();
        bookSpecification.setName(book.getName());
        bookSpecification.setPublication(book.getPublication());
        bookSpecification.setIsbn(book.getIsbn());
        bookSpecification.setEdition(book.getEdition());
        bookSpecification.setLanguage(book.getLanguage());
        bookSpecification.setEdition(book.getEdition());
        bookSpecification.setAuthor(authorRepo.findAuthorById(book.getAuthorId()).orElseThrow(NotFoundException::new));
        bookSpecification.setBookCategorySet(createCategory(book));
        bookSpecification = bookSpecificationService.saveBookSpec(bookSpecification);
        Book newBook =  new Book();
        log.info("Book Name : " +  bookSpecification.getName());
        log.info("Book ISBN : " +  bookSpecification.getIsbn());
        String serialNumber= book.getIsbn().concat("Book1");
        newBook.setSerialNo(serialNumber);
        newBook.setSpecification(bookSpecification);
        newBook.setStatus(Book.BookStatus.AVAILABLE);
        newBook.setUpdatedBy((Librarian) librarianService.getUser(book.getLibrarianId()));
        return bookRepository.save(newBook);
    }

    /**
     * Returns non-deleted Book of provided id.
     * @param id : Book id
     * @return Book :  Book records
     */
    @Override
    public Book getBook(Long id){
        log.info("Retrieving book by id " + id);
        Book book =  bookRepository.findBookById(id).orElseThrow(NotFoundException::new);
        return book;
    }

    /**
     *  Returns book record of given serial number
     * @param serialNo : Book Serial number
     * @return Book : Non-deleted Book
     */
    public Book getBookBySerialNumber(String serialNo){
        Book book = bookRepository.findBookBySerialNo(serialNo).orElseThrow(NotFoundException::new);
        return book;
    }

    /**
     *  Get all non-deleted books
     * @return List of Books
     */
    @Override
    public List<Book> getBooks(){
        log.info("All Book Id");
        List<Book> books = (List<Book>) bookRepository.findAll();
        books.removeIf(Book::isDeleteFlag);
        return books;
    }

    /**
     *  Updates Book. Here, the book updated means updating  specification.
     * @param bookDTO:  Book information to be updated.
     * @return BookSpecification: updated Book Specification
     */
    @Override
    public BookSpecification updateBook(BookDTO bookDTO){

        BookSpecification bookSpecification =  bookSpecificationService.getBookSpec(bookDTO.getBookId());
        if (bookSpecification == null){
            log.info("Book Record Not Found");
            throw  new NotFoundException();
        }
        if(bookDTO.getName() != null){
            log.info("Book Name changed");
            bookSpecification.setName(bookDTO.getName());
        }

        if(bookDTO.getPublication() != null) {
            log.info("Book publication changed");
            bookSpecification.setPublication(bookDTO.getPublication());
        }
        if(bookDTO.getIsbn() != null) {
            log.info("Book ISBN changed");
            bookSpecification.setIsbn(bookDTO.getIsbn());
        }
        if(bookDTO.getEdition() != null) {
            log.info("Book Edition changed");
            bookSpecification.setEdition(bookDTO.getEdition());
        }
        if(bookDTO.getLanguage() !=null) {
            log.info("Book Language changed");
            bookSpecification.setLanguage(bookDTO.getLanguage());
        }
        if(bookDTO.getAuthorId() == 0){
            log.info("Book Author Changed");
            bookSpecification.setAuthor(authorRepo.findAuthorById(bookDTO.getAuthorId()).orElseThrow(NotFoundException::new));
        }
        if (bookDTO.getBookCategory()  != null){
            log.info("Book Category changed");
            bookSpecification.setBookCategorySet(createCategory(bookDTO));
        }
        log.info("Update book  records:" + bookSpecification.getName());
        return bookSpecificationService.updateBookSpec(bookSpecification);

    }

    /**
     * Increase the count of already inserted book
     * @param isbn : Isbn of book
     * @param librarianId : Updating Librarian Id
     * @return Book: Book records of newly increased book
     */
    @Override
    public Book increaseBook(String isbn, long librarianId){
        log.info("Increasing Book .... ");
        List<Book> bookList = bookRepository.findBooksBySerialNoContaining(isbn);
        int bookCounter = bookList.size() + 1;
        Book newBook =  new Book();
        String newSerialNumber = generateNewSerial(isbn,bookCounter);
        log.info("Book Serial Number : " + newSerialNumber);
        newBook.setSerialNo(newSerialNumber);
        newBook.setStatus(Book.BookStatus.AVAILABLE);
        Librarian librarian = (Librarian) librarianService.getUser(librarianId);
        log.info("Updated by: " + librarian.getUsername());
        newBook.setUpdatedBy(librarian);

        newBook.setSpecification(bookList.get(0).getSpecification());
        bookRepository.save(newBook);
        return  newBook;
    }

    /**
     * Handles all book request,  Each request consist of book id and student id. It checks
     * book availability and creates new book loan record and book log records. The function
     * also changes the book availability.
     * @param bookRequestDTO : DTO for book request consist book id and user id
     * @return BookLoan : Book Loan created after request is successful
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public BookLoan requestForBook(BookRequestDTO bookRequestDTO) {
        log.info("Book Request");
        Book requestBook = getBook(bookRequestDTO.getBookId());
        log.info("Book requested: " + requestBook.getSpecification().getName());
        if (requestBook.getStatus() == Book.BookStatus.NOT_AVAILABLE){
            throw new BadRequestException();
        }
        requestBook.setStatus(Book.BookStatus.NOT_AVAILABLE);
        updateBook(requestBook);
        Student student  = (Student) studentService.getUser(bookRequestDTO.getUserId());
        int requestedBookCount = totalBookCount(student);

        if (requestedBookCount >= 10){
            throw  new BadRequestException();
        }
        Calendar date = Calendar.getInstance();
        date.setTimeZone(TimeZone.getTimeZone("CST"));

        BookLoan bookLoan =  new BookLoan();
        bookLoan.setStatus(BookLoan.LoanStatus.REQUESTED);
        bookLoan.setRequestedBy(student);
        bookLoan.setDateOfRequest(new Date());
        bookLoan.setBook(requestBook);
        bookLoan.setDateOfRequest(date.getTime());
        date.add(Calendar.MONTH,3);
        bookLoan.setDateOfReturn(date.getTime());
        log.info("Book Requested by " + student.getUsername());
        BookLog bookLog = createBookLog(BookLog.Action.REQUEST,bookLoan);
        Set<BookLog> bookLogSet =  new HashSet<>();
        bookLogSet.add(bookLog);
        bookLoan.setLog(bookLogSet);

        bookLoanRepository.save(bookLoan);

        return bookLoan;

    }

    /**
     * Searches book by name. Returns all list of book specification
     * with list of all available book ids.
     * @param bookName : Search query
     * @return List of BookSpecification
     */
    @Override
    public List<SearchDTO> searchBooks(String bookName){
        log.info("Searching Book by name ->  " + bookName);
        List<BookSpecification> bookSpecList = bookSpecificationService.searchByBookName(bookName);
        List <SearchDTO>  searchDTOS = convertSpecificationToSearchDTO(bookSpecList);
        return  searchDTOS;

    }

    /**
     *  Issues book which was requested previously by the user. It changes bookloan record
     *  and creates a new book log record.
     * @param bookIssueDTO : Dto with book Id and Librarian Id
     * @return BookLoan record
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public BookLoan issueBook(BookIssueDTO bookIssueDTO){
        Book issueBook =  getBook(bookIssueDTO.getBookId());
        log.info("Issuing book for -> " + issueBook.getId());

        if (issueBook == null){
            log.info("Issue Book not found");
            throw new NotFoundException();
        }
        BookLoan bookLoan = bookLoanRepository.findByBookAndStatus(issueBook,BookLoan.LoanStatus.REQUESTED);
        if (bookLoan == null){
            log.info("No Record Found for this book");
            throw new NotFoundException();
        }
        else if (bookLoan.getIssuedBy() != null) {
            log.info("Already Issued ");
            throw new BadRequestException();
        }

        Librarian librarian= (Librarian) librarianService.getUser(bookIssueDTO.getUserId());
        if (librarian == null){
            log.info("Invalid Librarian");
            throw  new NotFoundException();
        }
        bookLoan.setIssuedBy(librarian);
        bookLoan.setStatus(BookLoan.LoanStatus.ISSUED);
        BookLog bookLog =createBookLog(BookLog.Action.ISSUED,bookLoan);
        Set<BookLog> bookLogSet =  bookLoan.getLog();
        bookLogSet.add(bookLog);
        bookLoan.setLog(bookLogSet);
        bookLoanRepository.save(bookLoan);

        return bookLoan;
    }

    /**
     *  Handles return book request. It changes book status to Available, creates new book log record
     * @param bookReturnDTO : DTO with bookId
     * @return BookLoan record
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public BookLoan returnBook(BookReturnDTO bookReturnDTO){
        Book returnBook =getBookBySerialNumber(bookReturnDTO.getSerialNo());
        BookLoan bookLoan = bookLoanRepository.findByBookAndStatus(returnBook, BookLoan.LoanStatus.ISSUED);
        if (bookLoan == null){
            throw new NotFoundException();
        }
        else if (bookLoan.getIssuedBy() == null) {
            throw new BadRequestException();
        }

        bookLoan.setStatus(BookLoan.LoanStatus.RETURNED);
        Calendar date = Calendar.getInstance();
        date.setTimeZone(TimeZone.getTimeZone("CST"));
        bookLoan.setActualDateOfReturn(date.getTime());


        // CREATE BOOK LOG
        BookLog bookLog =createBookLog(BookLog.Action.RETURN,bookLoan);
        Set<BookLog> bookLogSet =  bookLoan.getLog();
        bookLogSet.add(bookLog);
        bookLoan.setLog(bookLogSet);
        bookLoanRepository.save(bookLoan);
        returnBook.setStatus(Book.BookStatus.AVAILABLE);
        updateBook(returnBook);
        return bookLoan;
    }


    /**
     * Creates Book Log of given action.
     * @param bookStatus : Status of action performed: Return, Request, Isssue
     * @param bookLoan : BookLoan
     * @return BookLog: log record for given action
     */
    private BookLog createBookLog(BookLog.Action bookStatus, BookLoan bookLoan){
        Calendar date = Calendar.getInstance();
        date.setTimeZone(TimeZone.getTimeZone("CST"));
        BookLog bookLog = new BookLog();
        bookLog.setAction(bookStatus);
        bookLog.setTimeStamp(date.getTime());
        bookLog.setBookLoan(bookLoan);
        return bookLog;
    }

    /**
     * Generates new serial number for books. It counts total books in a specification
     * and appends Book with count number  to create new book serial number.
     * @param isbn : Book Isbn
     * @param counter : Current Book Coonter
     * @return String: new serial number
     */
    private String generateNewSerial(String isbn, int counter){
        String serialNumber = isbn.concat("Book");
        return  serialNumber.concat(String.valueOf(counter));

    }

    /**
     *  Counts active request for the user.
     * @param user : Student
     * @return int : total number of active request
     */
    private int totalBookCount(User user){
        List<BookLoan> bookLoans = bookLoanRepository.findAllByRequestedByAndActualDateOfReturnIsNull(user);
        return  bookLoans.size();

    }

    /**
     * Converts SpecificationDTO to SearchDTO. It adds  list of available book ids  is search DTP
     * @param bookSpecList :  List of BookSpec
     * @return List of SearchDTO
     */
    private List<SearchDTO> convertSpecificationToSearchDTO(List<BookSpecification> bookSpecList){
        List<SearchDTO> searchDTOS = new ArrayList<>();
        for( BookSpecification bookSpec : bookSpecList){
            SearchDTO searchDTO = new SearchDTO();
            List<Book> bookList = bookRepository.findBooksBySpecificationAndStatusAndDeleteFlagFalse(bookSpec, Book.BookStatus.AVAILABLE);
            searchDTO.setId(bookSpec.getId());
            searchDTO.setAuthor(bookSpec.getAuthor());
            searchDTO.setBookCategorySet(bookSpec.getBookCategorySet());
            searchDTO.setEdition(bookSpec.getEdition());
            searchDTO.setLanguage(bookSpec.getLanguage());
            searchDTO.setPublication(bookSpec.getPublication());
            searchDTO.setName(bookSpec.getName());
            searchDTO.setIsbn(bookSpec.getIsbn());
            bookList.forEach( book -> searchDTO.getBookIds().add(book.getId()));
            searchDTOS.add(searchDTO);
        }
        return searchDTOS;
    }

    /**
     * Updates book.
     * @param book : Book to be updated
     * @return Updated Book
     */
    private Book updateBook(Book book){
        return bookRepository.save(book);
    }

    /**
     * Creates  category from BookDTO ids
     * @param bookDTO : DTO with ids for category
     * @return Set of BookCategory
     */
    private  Set<BookCategory> createCategory(BookDTO bookDTO){
        Set<BookCategory> bookCategories =  new HashSet<>();
        bookDTO.getBookCategory().forEach( s -> bookCategories.add(bookCategoryRepo.findById(s).orElseThrow(NotFoundException::new)));
        return bookCategories;
    }
}
