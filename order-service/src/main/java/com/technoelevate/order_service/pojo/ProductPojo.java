package com.technoelevate.order_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
public class ProductPojo {
	private String productId;
	
	private double productPrice;
	
	private String productName;
	
	private String productImage;
}
