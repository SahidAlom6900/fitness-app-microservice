package com.technoelevate.admin.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
	private boolean error;
	private String message;
	private Object data;
}
