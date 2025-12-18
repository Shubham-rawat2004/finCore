package com.rawat.FinCore.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Transaction;
import com.rawat.FinCore.Enum.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountOrToAccount(Account from, Account to);

    List<Transaction> findByFromAccountAccountIdOrToAccountAccountId(Long fromId, Long toId);

    List<Transaction> findByTxnDateBetween(LocalDateTime start, LocalDateTime end);

    List<Transaction> findByStatus(TransactionStatus status);
}
