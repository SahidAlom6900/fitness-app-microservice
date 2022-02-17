package com.technoelevate.role_service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technoelevate.role_service.entity.Role;
import com.technoelevate.role_service.pojo.RolePojo;
import com.technoelevate.role_service.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RolePojo addRole(RolePojo rolePojo) {
		Role role = new Role();
		if (rolePojo.getRoleName() == null)
			return null;
		BeanUtils.copyProperties(rolePojo, role);
		Role role0 = roleRepository.save(role);
		BeanUtils.copyProperties(role0, rolePojo);
		return rolePojo;
	}

	@Override
	public int getRole(String roleName) {
		Role role = roleRepository.findByRoleName(roleName);
		if (role != null) {
			return role.getRoleId();
		}
		return 0;
	}

	@Override
	public RolePojo getRole(int roleId) {
		Role role = roleRepository.findByRoleId(roleId);
		RolePojo rolePojo = new RolePojo();
		BeanUtils.copyProperties(role, rolePojo);
		System.out.println(rolePojo);
		return rolePojo;
	}
}
