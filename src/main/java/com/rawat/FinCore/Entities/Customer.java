// Customer.java
package com.rawat.FinCore.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 100)
    private String name;

    private LocalDate dateOfBirth;

    @Email
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 10)
    private String phone;

    @Column(length = 150)
    private String addressLine1;

    @Column(length = 150)
    private String addressLine2;

    @Column(length = 80)
    private String city;

    @Column(length = 80)
    private String state;

    @Column(length = 15)
    private String zipCode;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @OneToMany(mappedBy = "customer")
    private List<Loan> loans;

}
