package com.technoelevate.role_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.role_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleName(String roleName);

	Role findByRoleId(int roleId);
}
