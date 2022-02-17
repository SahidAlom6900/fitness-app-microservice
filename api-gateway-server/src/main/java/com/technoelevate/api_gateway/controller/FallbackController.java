package com.technoelevate.api_gateway.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.api_gateway.response.ResponseMessage;

@RestController
public class FallbackController {
	@GetMapping("user")
	public void name(@CookieValue(name = "user-id", defaultValue = "default-user-id") String userId) {
		System.out.println(userId);
	}
	
	@PostMapping("user")
	public void name123( ) {
		ResponseCookie springCookie = ResponseCookie.from("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t").httpOnly(true)
				.secure(true).path("/").maxAge(600).build();
		ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, springCookie.toString()).build();
	}

	@RequestMapping("/userFallBack/user")
	public ResponseEntity<ResponseMessage> userServiceFallBack(Exception exception) {
		return new ResponseEntity<>(
				new ResponseMessage(true,
						"User Service is taking too long to respond or is down. Please try again later", null),
				HttpStatus.OK);

	}

	@RequestMapping("/adminFallBack/admin")
	public ResponseEntity<ResponseMessage> adminServiceFallBack(Exception exception) {
		return new ResponseEntity<>(
				new ResponseMessage(true,
						"Admin Service is taking too long to respond or is down. Please try again later", null),
				HttpStatus.OK);
	}

	@RequestMapping("/roleFallBack/role")
	public ResponseEntity<ResponseMessage> roleServiceFallBack(Exception exception) {
		return new ResponseEntity<>(
				new ResponseMessage(true,
						"Role Service is taking too long to respond or is down. Please try again later", null),
				HttpStatus.OK);
	}

	@RequestMapping("/inventoryFallBack/inventory")
	public ResponseEntity<ResponseMessage> inventoryServiceFallBack(Exception exception) {
		return new ResponseEntity<>(
				new ResponseMessage(true,
						"Inventory Service is taking too long to respond or is down. Please try again later", null),
				HttpStatus.OK);
	}

	@RequestMapping("/orderFallBack/order")
	public ResponseEntity<ResponseMessage> orderServiceFallBack(Exception exception) {
		return new ResponseEntity<>(
				new ResponseMessage(true,
						"Order Service is taking too long to respond or is down. Please try again later", null),
				HttpStatus.OK);
	}

	@RequestMapping("/paymentFallBack/payment")
	public ResponseEntity<ResponseMessage> paymentServiceFallBack(Exception exception) {
		return new ResponseEntity<>(
				new ResponseMessage(true,
						"Payment Service is taking too long to respond or is down. Please try again later", null),
				HttpStatus.OK);
	}
}
