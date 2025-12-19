package com.rawat.FinCore.DTO;


import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private String role;
    private String accessToken;
    // getters and setters
}
