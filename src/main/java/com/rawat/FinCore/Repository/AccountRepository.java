package com.rawat.FinCore.Repository;

import java.util.List;
import java.util.Optional;

import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Enum.AccountStatus;
import com.rawat.FinCore.Enum.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByCustomer(Customer customer);

    List<Account> findByCustomerCustomerId(Long customerId);

    List<Account> findByStatus(AccountStatus status);

    List<Account> findByAccountType(AccountType accountType);
}
