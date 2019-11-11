package com.baylor.se.lms.data;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface UserBaseRepository<T> extends Repository<T, Long> {
    T findByUsername(String username);
}
