package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.TransactionRequest;
import com.rawat.FinCore.DTO.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse performTransaction(TransactionRequest request);
    List<TransactionResponse> getAccountTransactions(Long accountId);
}
