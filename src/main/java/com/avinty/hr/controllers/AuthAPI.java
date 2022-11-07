package com.avinty.hr.controllers;

import com.avinty.hr.dtos.JWTResponseDTO;
import com.avinty.hr.dtos.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/API/V1/auth")
@CrossOrigin(origins = "localhost:5000")
public interface AuthAPI {

    @PostMapping("/signin")
    ResponseEntity<JWTResponseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginRequest);
}
