package com.baylor.se.lms.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T> extends Repository<T, Long> {

    @Query("select  U from User U where U.username = ?1 and U.deleteFlag = false")
    Optional<T> findByUsername(String username);
    @Query("select  U from User U where U.email = ?1 and U.deleteFlag = false")
    Optional<T> findByEmail(String email);
}
