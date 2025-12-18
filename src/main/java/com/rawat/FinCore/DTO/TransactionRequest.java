package com.rawat.FinCore.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String txnType;       // DEPOSIT/WITHDRAWAL/TRANSFER/PAYMENT
    private Long fromAccountId;   // required for withdraw/transfer/payment
    private Long toAccountId;     // required for deposit/transfer/payment
    private BigDecimal amount;
    private String description;

}
