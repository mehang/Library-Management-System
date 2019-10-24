package com.baylor.se.lms.data;


import com.baylor.se.lms.BookLoan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookLoanRepository extends PagingAndSortingRepository<BookLoan, Long> {
}
