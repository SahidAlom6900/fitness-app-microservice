package com.technoelevate.order_service.response;

import java.util.List;

import com.technoelevate.order_service.pojo.ProductPojo;

import lombok.Data;

@Data
public class InventoryResponse {
	private List<ProductPojo> data;
}
