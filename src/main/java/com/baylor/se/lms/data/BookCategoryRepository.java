package com.baylor.se.lms.data;

import com.baylor.se.lms.model.BookCategory;
import org.springframework.data.repository.CrudRepository;

public interface BookCategoryRepository extends CrudRepository<BookCategory,Long> {

}
