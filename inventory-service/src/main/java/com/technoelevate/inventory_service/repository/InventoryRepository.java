package com.technoelevate.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevate.inventory_service.entity.Product;

public interface InventoryRepository extends JpaRepository<Product, Integer> {
	Product findByProductName(String productName);
	Product findByProductId(int productId);
}
