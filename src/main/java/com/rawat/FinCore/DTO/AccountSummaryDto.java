package com.rawat.FinCore.DTO;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountSummaryDto {
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
}

