package com.technoelevate.user_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolePojo {
	private String userName;
	private String password;
	private RolePojo rolePojo;
}
