package com.baylor.se.lms.controller;

import com.baylor.se.lms.data.PasswordResetTokenRepository;
import com.baylor.se.lms.dto.PasswordChangeDTO;
import com.baylor.se.lms.dto.PasswordForgotDTO;
import com.baylor.se.lms.model.Mail;
import com.baylor.se.lms.model.PasswordResetToken;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.service.impl.EmailService;
import com.baylor.se.lms.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity processForgotPasswordForm(@RequestBody PasswordForgotDTO passwordForgotDTO) {

        Optional<User> optionalUser = userService.findByEmail(passwordForgotDTO.getEmail());
        if (optionalUser.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email address.");
        }
        User user = optionalUser.get();
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("mehang_rai1@baylor.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "http://lms.com");
        model.put("resetUrl", "http://localhost:3000" + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail);

        return ResponseEntity.status(HttpStatus.OK).body("Password reset link sent to the email address.");
    }

}