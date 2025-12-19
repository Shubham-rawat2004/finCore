package com.rawat.FinCore.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {
    private String name;
    private LocalDate dateOfBirth;
    @Email
    private String email;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;

}

