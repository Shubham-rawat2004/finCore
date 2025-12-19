package com.rawat.FinCore.Controller;

import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.Transaction;
import com.rawat.FinCore.Service.AccountService;
import com.rawat.FinCore.Service.JwtService;
import com.rawat.FinCore.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRoleController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JwtService jwtService;

    // CUSTOMER can only see THEIR own accounts
    @GetMapping("/my-accounts")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Account>> getMyAccounts(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        Long customerId = jwtService.extractCustomerId(token);

        if (customerId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Account> accounts = accountService.findByCustomerCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    // CUSTOMER can only see transactions for THEIR account
    @GetMapping("/my-account/{accountId}/transactions")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Transaction>> getMyAccountTransactions(
            @PathVariable Long accountId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        Long customerId = jwtService.extractCustomerId(token);

        // Check if account belongs to this customer
        Account account = accountService.findById(accountId);
        if (account == null || !account.getCustomer().getCustomerId().equals(customerId)) {
            return ResponseEntity.status(403).build();
        }

        List<Transaction> transactions = transactionService.findByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }
}
