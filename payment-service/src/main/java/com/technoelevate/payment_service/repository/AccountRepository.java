package com.technoelevate.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.payment_service.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	Account findByAccountNumber(long accountNumber);
}
