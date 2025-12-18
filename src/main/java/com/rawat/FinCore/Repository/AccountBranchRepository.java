package com.rawat.FinCore.Repository;

import java.util.List;

import com.rawat.FinCore.Entities.Account;
import com.rawat.FinCore.Entities.AccountBranch;
import com.rawat.FinCore.Entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBranchRepository extends JpaRepository<AccountBranch, Long> {

    List<AccountBranch> findByAccount(Account account);

    List<AccountBranch> findByBranch(Branch branch);
}
