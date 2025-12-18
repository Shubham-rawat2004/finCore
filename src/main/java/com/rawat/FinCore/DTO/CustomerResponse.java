package com.rawat.FinCore.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private LocalDateTime createdAt;

}

