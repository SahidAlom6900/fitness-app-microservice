package com.technoelevate.order_service.pojo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Convert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.technoelevate.order_service.converter.StringListConverter;

import lombok.Data;

@Data
public class OrderPojo {
	
	private int orderId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
	private LocalDate orderDate;

	private int productCount;

	private double totalPrice;

	private String status;
	
	private int isDelivered;
	
	private boolean addCoin;

	@Convert(converter = StringListConverter.class)
	private List<String> productId;
	
	private int userId;
}
