package com.technoelevate.payment_service.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technoelevate.payment_service.entity.Account;
import com.technoelevate.payment_service.pojo.AccountPojo;
import com.technoelevate.payment_service.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public AccountPojo addUser(AccountPojo accountPojo) {
		
		
		
		
		long accountNumber = ThreadLocalRandom.current().nextLong(10000000000l, 100000000000l);
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account!=null) {
			return null;
		}
		account=new Account();
		accountPojo.setAccountNumber(accountNumber);
		BeanUtils.copyProperties(accountPojo, account);
		Account account2 = accountRepository.save(account);
		BeanUtils.copyProperties(account2, accountPojo);
		return accountPojo;
	}

	@Override
	public AccountPojo addBlance(AccountPojo accountPojo) {
		Account account = accountRepository.findByAccountNumber(accountPojo.getAccountNumber());
		if (account==null) {
			return null;
		}
		account.setBlance(account.getBlance()+accountPojo.getBlance());
		Account account2 = accountRepository.save(account);
		BeanUtils.copyProperties(account2, accountPojo);
		return accountPojo;
	}

	@Override
	public AccountPojo retriveBlance(AccountPojo accountPojo) {
		Account account = accountRepository.findByAccountNumber(accountPojo.getAccountNumber());
		double aviableBlance=account.getBlance()-accountPojo.getBlance();
		if ( aviableBlance<=500) {
			return null;
		}
		account.setBlance(aviableBlance);
		Account account2 = accountRepository.save(account);
		BeanUtils.copyProperties(account2, accountPojo);
		return accountPojo;
	}
}
