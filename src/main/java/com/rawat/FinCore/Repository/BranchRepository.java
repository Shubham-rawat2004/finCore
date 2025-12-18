package com.rawat.FinCore.Repository;

import java.util.Optional;

import com.rawat.FinCore.Entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByBranchCode(String branchCode);
}
