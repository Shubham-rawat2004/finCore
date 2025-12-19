package com.rawat.FinCore.DTO;


import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;       // ADMIN/BANKER/CUSTOMER
    private Long customerId;   // optional for CUSTOMER
}
