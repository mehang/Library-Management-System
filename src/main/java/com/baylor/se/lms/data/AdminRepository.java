package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Admin repository
 */
public interface AdminRepository extends  CrudRepository<Admin, Long> {
    @Query("select  A from Admin A where A.id = ?1 and A.deleteFlag = false")
    Optional<Admin> findAdminById(long id);
    List<Admin> findAllByDeleteFlagFalse();
}

