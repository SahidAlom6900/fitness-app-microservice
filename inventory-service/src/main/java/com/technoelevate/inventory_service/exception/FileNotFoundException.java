package com.technoelevate.inventory_service.exception;

@SuppressWarnings("serial")
public class FileNotFoundException extends RuntimeException {
   public	FileNotFoundException(String msg) {
	   super(msg);
   }
}
