package com.technoelevate.order_service.response;

import com.technoelevate.order_service.pojo.OrderPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderListResponse {
	private OrderPojo orderPojo;
}
