package com.technoelevate.admin.exceprion;

@SuppressWarnings("serial")
public class FallBackException extends RuntimeException {
	public FallBackException(String msg) {
		super(msg);
	}
}
