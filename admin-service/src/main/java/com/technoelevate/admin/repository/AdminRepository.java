package com.technoelevate.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin findByAdminId(int adminId);
}
