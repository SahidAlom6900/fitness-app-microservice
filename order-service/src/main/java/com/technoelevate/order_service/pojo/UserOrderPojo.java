package com.technoelevate.order_service.pojo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class UserOrderPojo {
	private LocalDate orderDate;

	private int productCount;

	private double totalPrice;

	private String status;
	
	private int isDelivered;

	private List<ProductPojo> productDetails;
}
