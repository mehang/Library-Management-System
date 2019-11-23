package com.baylor.se.lms.data;

import com.baylor.se.lms.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends UserBaseRepository<User>{
    @Query("select  U from User U where U.id = ?1 and U.deleteFlag = false")
    Optional<User> findUserById(Long id );

    @Query("select  U from User U where U.username = ?1 and U.deleteFlag = false")
    Optional<User> findUserByUsername(String username );


}
