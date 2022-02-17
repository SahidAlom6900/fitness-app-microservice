package com.technoelevate.order_service.controller;

import static com.technoelevate.order_service.common.OrderConstants.ADD_ORDER_SUCCESS_MESSAGE;
import static com.technoelevate.order_service.common.OrderConstants.ADD_ORDER_UNSUCCESS_MESSAGE;
import static com.technoelevate.order_service.common.OrderConstants.FETCH_USER_ORDER_SUCCESS_MESSAGE;
import static com.technoelevate.order_service.common.OrderConstants.FETCH_USER_ORDER_UNSUCCESS_MESSAGE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.order_service.pojo.OrderPojo;
import com.technoelevate.order_service.pojo.UserOrderPojo;
import com.technoelevate.order_service.response.ResponseMessage;
import com.technoelevate.order_service.service.OrderService;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<ResponseMessage> addOrderToUser(@RequestBody OrderPojo orderPojo) {
		OrderPojo orderPojo0 = orderService.addOrderToUser(orderPojo);
		if (orderPojo0 != null)
			return new ResponseEntity<>(new ResponseMessage(false, ADD_ORDER_SUCCESS_MESSAGE, orderPojo0), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, ADD_ORDER_UNSUCCESS_MESSAGE, orderPojo0), HttpStatus.OK);
	}

	@GetMapping("{userId}")
	public ResponseEntity<ResponseMessage> getUserOrder(@PathVariable("userId") int userId) {
		List<UserOrderPojo> userOrder = orderService.getUserOrder(userId);
		if (userOrder != null)
			return new ResponseEntity<>(new ResponseMessage(false, FETCH_USER_ORDER_SUCCESS_MESSAGE, userOrder), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, FETCH_USER_ORDER_UNSUCCESS_MESSAGE, userOrder), HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<ResponseMessage> getUserOrderDemo(@PathVariable("userId") int userId) {
		List<UserOrderPojo> userOrder = orderService.getUserOrderDemo(userId);
		if (userOrder != null)
			return new ResponseEntity<>(new ResponseMessage(false, FETCH_USER_ORDER_SUCCESS_MESSAGE, userOrder), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, FETCH_USER_ORDER_UNSUCCESS_MESSAGE, userOrder), HttpStatus.OK);
	}

}
