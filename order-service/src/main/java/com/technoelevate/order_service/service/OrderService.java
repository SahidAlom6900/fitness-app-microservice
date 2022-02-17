package com.technoelevate.order_service.service;

import java.util.List;

import com.technoelevate.order_service.pojo.OrderPojo;
import com.technoelevate.order_service.pojo.UserOrderPojo;

public interface OrderService {

	OrderPojo addOrderToUser(OrderPojo orderPojo);

	List<UserOrderPojo> getUserOrder(int userId);

	List<UserOrderPojo> getUserOrderDemo(int userId);

}
