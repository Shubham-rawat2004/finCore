package com.rawat.FinCore.DTO;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateRequest {
    private Long customerId;
    private String accountType;     // SAVINGS/CURRENT/FD/LOAN_ACCOUNT
    private BigDecimal initialDeposit;

}

