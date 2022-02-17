package com.technoelevate.user_service.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePojo {
	private Integer roleId;

	private String roleName;

	private List<PermissionPojo> permissions;
}
