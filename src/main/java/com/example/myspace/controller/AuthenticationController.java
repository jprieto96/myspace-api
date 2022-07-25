package com.example.myspace.controller;

import com.example.myspace.util.Constants;
import com.example.myspace.dto.AuthenticationDto;
import com.example.myspace.model.ClientGroupModel;
import com.example.myspace.security.UserPrinciple;
import com.example.myspace.util.TokenResultUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto authenticationDto) throws AuthenticationException {
        // Authentication is realized with the username and password
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getEmail(),
                        authenticationDto.getPassword()
                )
        );
        // The authentication object is saved in the context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Token is generated
        final String token = generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResultUtil(token));
    }

    private String generateToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        // The userGroups are equals to the roles
        ClientGroupModel clientGroupModel = userPrinciple.getClientGroupModel();
        String userGroupName = clientGroupModel.getName();

        return Jwts.builder()
                .claim("id", userPrinciple.getId())
                .setSubject(authentication.getName())
                .claim(Constants.AUTHORITIES_USER_GROUP, userGroupName)
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .compact();
    }

}