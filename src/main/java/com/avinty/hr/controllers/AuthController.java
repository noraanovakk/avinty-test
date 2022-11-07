package com.avinty.hr.controllers;

import com.avinty.hr.dtos.JWTResponseDTO;
import com.avinty.hr.dtos.LoginDTO;
import com.avinty.hr.security.JWTUtils;
import com.avinty.hr.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthAPI{
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<JWTResponseDTO> authenticateUser(LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JWTResponseDTO(
                userDetails.getId(),
                jwt,
                userDetails.getUsername(),
                userDetails.getAuthorities()
        ));
    }
}
