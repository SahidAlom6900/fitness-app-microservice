package com.technoelevate.user_service.response;

import com.technoelevate.user_service.pojo.RolePojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
	private RolePojo data;	
}
