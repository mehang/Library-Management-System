package com.baylor.se.lms.data;

import com.baylor.se.lms.model.BookLog;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Book Log
 */
public interface BookLogRepository extends CrudRepository<BookLog, Long> {
}
