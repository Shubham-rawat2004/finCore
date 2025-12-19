package com.rawat.FinCore.Controller;

import com.rawat.FinCore.DTO.AccountCreateRequest;
import com.rawat.FinCore.DTO.AccountResponse;
import com.rawat.FinCore.DTO.AccountSummaryDto;
import com.rawat.FinCore.Service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody AccountCreateRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public AccountResponse getOne(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/by-customer/{customerId}")
    public List<AccountSummaryDto> getByCustomer(@PathVariable Long customerId) {
        return accountService.getCustomerAccounts(customerId);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id,
                                        @RequestParam BigDecimal amount) {
        accountService.deposit(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id,
                                         @RequestParam BigDecimal amount) {
        accountService.withdraw(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestParam Long fromAccountId,
                                         @RequestParam Long toAccountId,
                                         @RequestParam BigDecimal amount) {
        accountService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok().build();
    }
}
