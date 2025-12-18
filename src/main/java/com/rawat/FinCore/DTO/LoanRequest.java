package com.rawat.FinCore.DTO;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanRequest {
    private Long customerId;
    private Long accountId; // optional
    private String loanType;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
}

