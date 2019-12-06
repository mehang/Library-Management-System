package com.baylor.se.lms.data;

import com.baylor.se.lms.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for PasswordTOken. USed for reset password token
 */
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);
}
