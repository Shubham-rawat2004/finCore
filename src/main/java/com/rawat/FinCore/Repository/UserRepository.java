package com.rawat.FinCore.Repository;

import java.util.Optional;

import com.rawat.FinCore.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // custom method not provided by JpaRepository
}
