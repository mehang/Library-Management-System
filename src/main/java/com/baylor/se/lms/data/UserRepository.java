package com.baylor.se.lms.data;

import com.baylor.se.lms.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User>{
}
