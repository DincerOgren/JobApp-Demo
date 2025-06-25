package com.jobapp.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String email;
    String name;
    String token;
    Set<String> roles;
}
