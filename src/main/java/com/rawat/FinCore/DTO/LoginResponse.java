package com.rawat.FinCore.DTO;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String role;
    private String token; // if you add JWT later

}

