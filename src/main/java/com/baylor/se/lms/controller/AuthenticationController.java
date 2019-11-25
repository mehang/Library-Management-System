package com.baylor.se.lms.controller;

import com.baylor.se.lms.dto.LoginDTO;
import com.baylor.se.lms.model.AuthToken;
import com.baylor.se.lms.model.User;
import com.baylor.se.lms.security.TokenProvider;
import com.baylor.se.lms.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
//@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody LoginDTO loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        Optional<User> user = userService.findByUsername(loginUser.getUsername());
        Map<Object, Object> authModel = new HashMap<>();
        if (user.isPresent()){
            User loggedUser = user.get();
            authModel.put("token", token);
            authModel.put("username", loggedUser.getUsername());
            authModel.put("userPK", loggedUser.getId());
            authModel.put("type", loggedUser.getType());
        }
        return ResponseEntity.ok(authModel);
    }

}

