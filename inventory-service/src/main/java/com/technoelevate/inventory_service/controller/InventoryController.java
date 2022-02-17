package com.technoelevate.inventory_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technoelevate.inventory_service.pojo.ProductPojo;
import com.technoelevate.inventory_service.response.ResponseMessage;
import com.technoelevate.inventory_service.service.InventoryService;

@RestController
@RequestMapping("api/v1/product")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@PostMapping
	public ResponseEntity<ResponseMessage> addProducts(@RequestParam("files") List<MultipartFile> files,
			@RequestParam("data") String data) {
		List<ProductPojo> productPojos = inventoryService.addProduct(files, data);
		if (!productPojos.isEmpty())
			return new ResponseEntity<>(new ResponseMessage(false, "", productPojos), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", productPojos), HttpStatus.OK);
	}// end addProducts method

	@GetMapping
	public ResponseEntity<ResponseMessage> getAllProduct() {
		List<ProductPojo> productPojos = inventoryService.fetchProduct();
		if (!productPojos.isEmpty())
			return new ResponseEntity<>(new ResponseMessage(false, "", productPojos), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", productPojos), HttpStatus.OK);
	}// end getAllProduct method

	@PostMapping("/order/{productCount}")
	public ResponseEntity<ResponseMessage> addProductToOrder(@RequestBody List<Integer> productIds,
			@PathVariable("productCount") int productCount) {
		List<ProductPojo> productPojos = inventoryService.fetchProductbyIdsAndSaveProduct(productIds, productCount);
		if (!productPojos.isEmpty())
			return new ResponseEntity<>(new ResponseMessage(false, "", productPojos), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", productPojos), HttpStatus.OK);
	}// end getAllProduct method

	@PostMapping("/order")
	public ResponseEntity<ResponseMessage> getProductToOrder(@RequestBody List<List<Integer>> productIds) {
		List<List<ProductPojo>> productPojos = inventoryService.fetchProductbyIds(productIds);
		if (!productPojos.isEmpty())
			return new ResponseEntity<>(new ResponseMessage(false, "", productPojos), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", productPojos), HttpStatus.OK);
	}// end getAllProduct method

	@PostMapping("/orders")
	public ResponseEntity<ResponseMessage> getAllProductToOrder(@RequestBody List<Integer> productAllIds) {
		List<ProductPojo> productPojos = inventoryService.fetchAllProductbyIds(productAllIds);
		if (!productPojos.isEmpty())
			return new ResponseEntity<>(new ResponseMessage(false, "", productPojos), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, "", productPojos), HttpStatus.OK);
	}// end getAllProduct method

}
