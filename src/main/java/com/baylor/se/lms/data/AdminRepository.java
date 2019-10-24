package com.baylor.se.lms.data;

import com.baylor.se.lms.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdminRepository  extends PagingAndSortingRepository<Admin, Long> {
}
