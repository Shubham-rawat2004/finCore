// Branch.java
package com.rawat.FinCore.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String branchCode;

    @Column(length = 200)
    private String address;

    @Column(length = 20)
    private String phone;

    @OneToMany(mappedBy = "branch")
    private List<AccountBranch> accountBranches;

}
