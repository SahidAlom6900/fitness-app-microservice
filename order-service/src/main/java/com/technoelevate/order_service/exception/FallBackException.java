package com.technoelevate.order_service.exception;

@SuppressWarnings("serial")
public class FallBackException extends RuntimeException {
	public FallBackException(String msg) {
		super(msg);
	}
}
