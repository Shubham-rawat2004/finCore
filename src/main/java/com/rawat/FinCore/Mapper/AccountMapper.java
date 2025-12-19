package com.rawat.FinCore.Mapper;

import com.rawat.FinCore.DTO.AccountCreateRequest;
import com.rawat.FinCore.DTO.AccountResponse;
import com.rawat.FinCore.DTO.AccountSummaryDto;
import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Enum.AccountStatus;
import com.rawat.FinCore.Enum.AccountType;
import lombok.Data;

import java.time.LocalDate;

@Data
public final class AccountMapper {

    private AccountMapper() {
    }

    public static Account toNewEntity(AccountCreateRequest dto, Customer customer, String accountNumber) {
        Account a = new Account();
        a.setCustomer(customer);
        a.setAccountNumber(accountNumber);
        a.setAccountType(AccountType.valueOf(dto.getAccountType()));
        a.setBalance(dto.getInitialDeposit());
        a.setStatus(AccountStatus.ACTIVE);
        a.setOpenedDate(LocalDate.now());
        return a;
    }

    public static AccountResponse toDto(Account a) {
        AccountResponse dto = new AccountResponse();
        dto.setId(a.getAccountId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setAccountType(a.getAccountType().name());
        dto.setBalance(a.getBalance());
        dto.setStatus(a.getStatus().name());
        dto.setOpenedDate(a.getOpenedDate());
        dto.setClosedDate(a.getClosedDate());
        dto.setCustomerId(a.getCustomer().getCustomerId());
        return dto;
    }

    public static AccountSummaryDto toSummary(Account a) {
        AccountSummaryDto dto = new AccountSummaryDto();
        dto.setId(a.getAccountId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setAccountType(a.getAccountType().name());
        dto.setBalance(a.getBalance());
        return dto;
    }
}
