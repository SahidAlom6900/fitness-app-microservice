package com.technoelevate.user_service.pojo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderPojo {
	private int userId;
	
	private LocalDate orderDate;

	private int productCount;

	private double totalPrice;

	private String status;
	
	private int isDelivered;

	private List<ProductPojo> productDetails;
}
