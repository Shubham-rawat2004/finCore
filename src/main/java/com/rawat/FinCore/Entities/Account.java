package com.rawat.FinCore.Entities;

import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Enum.AccountStatus;
import com.rawat.FinCore.Enum.AccountType;
import jakarta.persistence.*;
import com.rawat.FinCore.Entities.Transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    private LocalDate openedDate;

    private LocalDate closedDate;

    // In the database:customer_id cannot be NULL.
    //If you try to save an Account without setting a customer, Hibernate will throw an exception.
    @ManyToOne(optional = false)   // An Account cannot exist without a Customer.
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> incomingTransactions;

    @OneToMany(mappedBy = "account")
    private List<Loan> loans;

    @OneToMany(mappedBy = "account")
    private List<AccountBranch> accountBranches;

}
