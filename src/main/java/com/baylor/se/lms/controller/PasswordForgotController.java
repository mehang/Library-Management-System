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




}