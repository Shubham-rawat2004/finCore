package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.AccountCreateRequest;
import com.rawat.FinCore.DTO.AccountResponse;
import com.rawat.FinCore.DTO.AccountSummaryDto;
import com.rawat.FinCore.Entities.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountCreateRequest request);
    List<Account> findByCustomerCustomerId(Long customerId);

    Account findById(Long id);

    AccountResponse getAccount(Long id);

    List<AccountSummaryDto> getCustomerAccounts(Long customerId);

    void deposit(Long accountId, BigDecimal amount);

    void withdraw(Long accountId, BigDecimal amount);

    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
