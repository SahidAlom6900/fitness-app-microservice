package com.technoelevate.inventory_service.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
	public	NotFoundException(String msg) {
		   super(msg);
	   }
}
