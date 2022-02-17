package com.technoelevate.payment_service.pojo;

import lombok.Data;

@Data
public class AccountPojo {
	private int accountId;

	private long accountNumber;

	private String userName;

	private String password;

	private double blance;
}
