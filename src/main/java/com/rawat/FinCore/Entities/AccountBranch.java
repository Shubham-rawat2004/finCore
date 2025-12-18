package com.rawat.FinCore.Entities;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "account_branch")
public class AccountBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private LocalDate assignedDate;

}
