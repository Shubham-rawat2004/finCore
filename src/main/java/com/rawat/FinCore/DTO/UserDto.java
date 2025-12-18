package com.rawat.FinCore.DTO;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String role;      // ADMIN/BANKER/CUSTOMER
    private Long customerId;  // nullable
}

