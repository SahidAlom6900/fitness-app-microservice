package com.technoelevate.inventory_service.exception;

@SuppressWarnings("serial")
public class ProductOutOfStock extends RuntimeException {
	public	ProductOutOfStock(String msg) {
		   super(msg);
	   }
}
