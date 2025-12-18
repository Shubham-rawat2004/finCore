package com.rawat.FinCore.DTO;


import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanResponse {
    private Long id;
    private String loanType;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long customerId;
    private Long accountId;
}

