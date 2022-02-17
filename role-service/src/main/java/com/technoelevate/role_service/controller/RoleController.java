package com.technoelevate.role_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.role_service.pojo.RolePojo;
import com.technoelevate.role_service.response.ResponseMessage;
import com.technoelevate.role_service.service.RoleService;

@RestController
@RequestMapping("api/v1")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@PostMapping("role")
	public ResponseEntity<ResponseMessage> addRolePojo(@RequestBody RolePojo rolePojo) {
		RolePojo rolePojo2 = roleService.addRole(rolePojo);
		if (rolePojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", rolePojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", rolePojo2), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("role/{roleName}")
	public ResponseEntity<ResponseMessage> getRolePojo(@PathVariable("roleName") String roleName) {
		int rolePojo2 = roleService.getRole(roleName);
		if (rolePojo2 > 0)
			return new ResponseEntity<>(new ResponseMessage(false, "", rolePojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", rolePojo2), HttpStatus.OK);
	}

	@PostMapping("role/{roleId}")
	public ResponseEntity<ResponseMessage> getRolePojo(@PathVariable("roleId") int roleId) {
		RolePojo rolePojo = roleService.getRole(roleId);
		if (rolePojo != null)
			return new ResponseEntity<>(new ResponseMessage(false, "", rolePojo), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", rolePojo), HttpStatus.OK);
	}
}
