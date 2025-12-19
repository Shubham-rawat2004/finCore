package com.rawat.FinCore.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Transaction;
import com.rawat.FinCore.Enum.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountOrToAccount(Account from, Account to);

    List<Transaction> findByFromAccountAccountIdOrToAccountAccountId(Long fromId, Long toId);

    List<Transaction> findByTxnDateBetween(LocalDateTime start, LocalDateTime end);

    List<Transaction> findByStatus(TransactionStatus status);

    // *** FIXED: Use @Query since accountId is direct column ***
    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId")
    List<Transaction> findByAccountId(@Param("accountId") Long accountId);
}
