package com.technoelevate.inventory_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
public class ProductPojo {
	private int productId;
	private String productName;
	private double productPrice;
	private int productCount;
	private String productImage;
}
