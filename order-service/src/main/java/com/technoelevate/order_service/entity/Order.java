package com.technoelevate.order_service.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.technoelevate.order_service.converter.StringListConverter;

import lombok.Data;
  
@SuppressWarnings("serial")
@Data
@Entity
@Table(name="order_details")
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
	private LocalDate orderDate;
	
	private int productCount;
	
	private double totalPrice;
	
	private String status;
	
	private int isDelivered;
	
	@Convert(converter = StringListConverter.class)
	private List<String> productId;
	
	private int userId;
}
