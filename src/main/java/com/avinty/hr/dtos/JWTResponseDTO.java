package com.avinty.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class JWTResponseDTO {
    private long id;
    private String token;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
}
