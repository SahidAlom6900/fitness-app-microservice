package com.technoelevate.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.admin.entity.Coach;

public interface CoachRepository extends JpaRepository<Coach, Integer> {
	Coach findByCoachId(int coachId);
}
