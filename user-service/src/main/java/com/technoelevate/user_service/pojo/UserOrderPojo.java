package com.technoelevate.user_service.pojo;

import java.util.List;

import lombok.Data;

@Data
public class UserOrderPojo {
	private String userName;
	private String email;
	private long phoneNumber;
	private String address;
	private String gender;
	private List<OrderPojo> order;
}
