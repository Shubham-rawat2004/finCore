package com.rawat.FinCore.Mapper;


import com.rawat.FinCore.DTO.TransactionRequest;
import com.rawat.FinCore.DTO.TransactionResponse;
import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Transaction;
import com.rawat.FinCore.Enum.TransactionStatus;
import com.rawat.FinCore.Enum.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class TransactionMapper {

    private TransactionMapper() {
    }

    public static Transaction toNewEntity(TransactionRequest dto,
                                          Account from,
                                          Account to) {
        Transaction t = new Transaction();
        t.setTxnType(TransactionType.valueOf(dto.getTxnType()));
        t.setAmount(dto.getAmount());
        t.setDescription(dto.getDescription());
        t.setTxnDate(LocalDateTime.now());
        t.setStatus(TransactionStatus.PENDING); // service will update to COMPLETED on success
        t.setFromAccount(from);
        t.setToAccount(to);
        return t;
    }

    public static TransactionResponse toDto(Transaction t) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(t.getTxnId());
        dto.setTxnType(t.getTxnType().name());
        dto.setAmount(t.getAmount());
        dto.setTxnDate(t.getTxnDate());
        dto.setDescription(t.getDescription());
        dto.setStatus(t.getStatus().name());
        dto.setFromAccountId(t.getFromAccount() != null ? t.getFromAccount().getAccountId() : null);
        dto.setToAccountId(t.getToAccount() != null ? t.getToAccount().getAccountId() : null);
        return dto;
    }
}

