package com.technoelevate.inventory_service.pojo;

import java.util.List;

import lombok.Data;

@Data
public class ProductList {
	private List<ProductPojo> products;
}
