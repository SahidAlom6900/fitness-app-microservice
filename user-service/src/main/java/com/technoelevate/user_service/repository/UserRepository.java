package com.technoelevate.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserName(String userName);

	User findByUserId(int userId);
}
