package com.rawat.FinCore.Repository;

import java.util.Optional;

import com.rawat.FinCore.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}
