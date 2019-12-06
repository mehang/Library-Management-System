package com.baylor.se.lms.data;

import com.baylor.se.lms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for User Entity
 */
public interface UserRepository extends CrudRepository<User,Long>{
    @Query("select  U from User U where U.id = ?1 and U.deleteFlag = false")
    Optional<User> findUserById(Long id );

    @Query("select  U from User U where U.username = ?1 and U.deleteFlag = false")
    Optional<User> findUserByUsername(String username );

    @Query("select  U from User U where U.email = ?1 and U.deleteFlag = false")
    Optional<User> findUserByEmail(String email );
}
