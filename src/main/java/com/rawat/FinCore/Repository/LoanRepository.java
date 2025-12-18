package com.rawat.FinCore.Repository;

import java.util.List;

import com.rawat.FinCore.Entities.Customer;
import com.rawat.FinCore.Entities.Loan;
import com.rawat.FinCore.Enum.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomer(Customer customer);

    List<Loan> findByCustomerCustomerId(Long customerId);

    List<Loan> findByStatus(LoanStatus status);
}
