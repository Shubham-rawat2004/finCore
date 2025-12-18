package com.rawat.FinCore.DTO;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private String txnType;
    private BigDecimal amount;
    private LocalDateTime txnDate;
    private String description;
    private String status;
    private Long fromAccountId;
    private Long toAccountId;


}
