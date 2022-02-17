package com.technoelevate.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.payment_service.pojo.AccountPojo;
import com.technoelevate.payment_service.response.ResponseMessage;
import com.technoelevate.payment_service.service.AccountService;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<ResponseMessage> addUser(@RequestBody AccountPojo accountPojo) {
		AccountPojo accountPojo2 = accountService.addUser(accountPojo);
		if (accountPojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", accountPojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", accountPojo2), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("blance")
	public ResponseEntity<ResponseMessage> addBlance(@RequestBody AccountPojo accountPojo) {
		AccountPojo accountPojo2 = accountService.addBlance(accountPojo);
		if (accountPojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", accountPojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", accountPojo2), HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("retrive")
	public ResponseEntity<ResponseMessage> retriveBlance(@RequestBody AccountPojo accountPojo) {
		AccountPojo accountPojo2 = accountService.retriveBlance(accountPojo);
		if (accountPojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", accountPojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", accountPojo2), HttpStatus.OK);
	}
}
