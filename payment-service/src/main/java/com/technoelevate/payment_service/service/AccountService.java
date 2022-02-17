package com.technoelevate.payment_service.service;

import com.technoelevate.payment_service.pojo.AccountPojo;

public interface AccountService {
	AccountPojo addUser(AccountPojo accountPojo);

	AccountPojo addBlance(AccountPojo accountPojo);

	AccountPojo retriveBlance(AccountPojo accountPojo);
}
