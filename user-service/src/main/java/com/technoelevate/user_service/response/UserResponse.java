package com.technoelevate.user_service.response;

import java.time.LocalDate;
import java.util.List;

import com.technoelevate.user_service.pojo.CoachPojo2;
import com.technoelevate.user_service.pojo.PlanPojo;

import lombok.Data;

@Data
public class UserResponse {
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

	private List<CoachPojo2> coachPojo;

	private PlanPojo planPojo;
}
