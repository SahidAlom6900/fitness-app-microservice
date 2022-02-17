package com.technoelevate.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.admin.entity.Transformation;

public interface TransformationRepository extends JpaRepository<Transformation, Integer> {
	Transformation findByTransId(int transId);
}
