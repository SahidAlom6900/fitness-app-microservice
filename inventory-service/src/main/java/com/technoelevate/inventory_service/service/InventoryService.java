package com.technoelevate.inventory_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.inventory_service.pojo.ProductPojo;

public interface InventoryService {

	List<ProductPojo> addProduct(List<MultipartFile> files, String data);

	List<ProductPojo> fetchProduct();

	List<ProductPojo> fetchProductbyIdsAndSaveProduct(List<Integer> productIds, int productCount);

	List<ProductPojo> fetchAllProductbyIds(List<Integer> productIds);

	List<List<ProductPojo>> fetchProductbyIds(List<List<Integer>> productIds);
}
