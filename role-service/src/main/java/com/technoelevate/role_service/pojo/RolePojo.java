package com.technoelevate.role_service.pojo;

import java.util.List;

import com.technoelevate.role_service.entity.Permission;

import lombok.Data;

@Data
public class RolePojo {
	private Integer roleId;

	private String roleName;

	private List<Permission> permissions;
}
