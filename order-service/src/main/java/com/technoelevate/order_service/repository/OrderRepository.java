package com.technoelevate.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.order_service.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByUserId(int userId);
}
