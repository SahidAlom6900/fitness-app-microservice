package com.technoelevate.user_service.exception;

@SuppressWarnings("serial")
public class NotFoundResponse extends RuntimeException {
	public NotFoundResponse(String msg) {
		super(msg);
	}
}
