package com.rawat.FinCore.Service;

import com.rawat.FinCore.DTO.AccountCreateRequest;
import com.rawat.FinCore.DTO.AccountResponse;
import com.rawat.FinCore.DTO.AccountSummaryDto;
import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Mapper.AccountMapper;
import com.rawat.FinCore.Repository.AccountRepository;
import com.rawat.FinCore.Repository.CustomerRepository;
import com.rawat.FinCore.Enum.AccountStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AccountResponse createAccount(AccountCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        String accountNumber = generateAccountNumber();
        Account account = AccountMapper.toNewEntity(request, customer, accountNumber);
        Account saved = accountRepository.save(account);
        return AccountMapper.toDto(saved);
    }

    @Override
    public List<Account> findByCustomerCustomerId(Long customerId) {
        return accountRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    @Override
    public AccountResponse getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return AccountMapper.toDto(account);
    }

    @Override
    public List<AccountSummaryDto> getCustomerAccounts(Long customerId) {
        List<Account> accounts = accountRepository.findByCustomerCustomerId(customerId);
        return accounts.stream()
                .map(AccountMapper::toSummary)
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        ensureActive(account);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        ensureActive(account);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Override
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account from = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account to = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        ensureActive(from);
        ensureActive(to);
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);
    }

    private void ensureActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
    }

    private String generateAccountNumber() {
        return "ACCT-" + System.currentTimeMillis();
    }
}
