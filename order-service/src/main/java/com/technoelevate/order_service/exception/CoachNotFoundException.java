package com.technoelevate.order_service.exception;

@SuppressWarnings("serial")
public class CoachNotFoundException extends RuntimeException {
	public CoachNotFoundException(String msg) {
		super(msg);
	}
}
