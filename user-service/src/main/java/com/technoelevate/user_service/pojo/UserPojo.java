package com.technoelevate.user_service.pojo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class UserPojo {
	private int userId;
	private String userName;
	private LocalDate dob;
	private String email;
	private long phoneNumber;
	private String address;
	private String password;
	private String gender;
	private int steps;
	private int coins;

	private List<String> coachId;

	private int planId;

	private int roleId;
	
	private double totalPrice;
}
