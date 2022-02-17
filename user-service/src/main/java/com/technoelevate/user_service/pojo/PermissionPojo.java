package com.technoelevate.user_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionPojo {
	private int permissionId;
	private String permissionName;
}
