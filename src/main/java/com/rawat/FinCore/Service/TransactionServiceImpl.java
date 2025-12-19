package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.TransactionRequest;
import com.rawat.FinCore.DTO.TransactionResponse;
import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Transaction;
import com.rawat.FinCore.Enum.TransactionStatus;
import com.rawat.FinCore.Mapper.TransactionMapper;
import com.rawat.FinCore.Repository.AccountRepository;
import com.rawat.FinCore.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository,
                                  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse performTransaction(TransactionRequest request) {
        Account from = null;
        Account to = null;

        if (request.getFromAccountId() != null) {
            from = accountRepository.findById(request.getFromAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        }
        if (request.getToAccountId() != null) {
            to = accountRepository.findById(request.getToAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("Target account not found"));
        }

        Transaction txn = TransactionMapper.toNewEntity(request, from, to);

        // Basic routing to AccountService rules could be added, but here we just mark as completed
        txn.setStatus(TransactionStatus.COMPLETED);
        Transaction saved = transactionRepository.save(txn);

        return TransactionMapper.toDto(saved);
    }

    @Override
    public List<TransactionResponse> getAccountTransactions(Long accountId) {
        List<Transaction> txns =
                transactionRepository.findByFromAccountAccountIdOrToAccountAccountId(accountId, accountId);

        return txns.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }
}
