package com.baylor.se.lms.data;

import com.baylor.se.lms.model.Admin;
import com.baylor.se.lms.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

//public interface AdminRepository  extends PagingAndSortingRepository<Admin, Long> {
//    @Query("select  A from Admin A where A.id = ?1 and A.deleteFlag = false")
//    Optional<Admin> findAdminById(double id);
//
//    Optional <List<Admin>> findAllByDeleteFlagFalse();
//}

public interface AdminRepository extends UserBaseRepository<Admin>, CrudRepository<Admin, Long> {
    @Query("select  A from Admin A where A.id = ?1 and A.deleteFlag = false")
    Optional<Admin> findAdminById(long id);

    Optional <List<Admin>> findAllByDeleteFlagFalse();
}

