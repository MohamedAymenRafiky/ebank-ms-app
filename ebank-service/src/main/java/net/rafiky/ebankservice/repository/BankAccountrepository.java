package net.rafiky.ebankservice.repository;

import net.rafiky.ebankservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountrepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomerId(long customerId);
}
