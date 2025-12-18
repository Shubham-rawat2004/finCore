package com.rawat.FinCore.DTO;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String status;
    private LocalDate openedDate;
    private LocalDate closedDate;
    private Long customerId;
}

