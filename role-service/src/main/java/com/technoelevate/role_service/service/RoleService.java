package com.technoelevate.role_service.service;

import com.technoelevate.role_service.pojo.RolePojo;

public interface RoleService {

	RolePojo addRole(RolePojo rolePojo);

	int getRole(String roleName);

	RolePojo getRole(int roleId);
}
