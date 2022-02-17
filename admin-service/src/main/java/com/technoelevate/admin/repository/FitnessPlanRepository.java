package com.technoelevate.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.admin.entity.FitnessPlan;

public interface FitnessPlanRepository extends JpaRepository<FitnessPlan, Integer> {
	FitnessPlan findByPlanId(int planId);
}
