package com.technoelevate.inventory_service.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.inventory_service.entity.Product;
import com.technoelevate.inventory_service.exception.FileNotFoundException;
import com.technoelevate.inventory_service.exception.NotFoundException;
import com.technoelevate.inventory_service.exception.ProductOutOfStock;
import com.technoelevate.inventory_service.pojo.ProductPojo;
import com.technoelevate.inventory_service.repository.InventoryRepository;

@Service
public class InventoryServiceimpl implements InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ObjectMapper mappper;
	@Value("${file.upload.location:C:\\fitness}")
	private String dir;
	private Path dirLocation;

	private Path getPath(String fileName) {
		return Paths.get(this.dir + "\\" + fileName).toAbsolutePath().normalize();
	}

	private String addFile(MultipartFile multipartFile) {
		try {
			if (multipartFile != null) {
				String filename = multipartFile.getOriginalFilename();
				Path filePath = dirLocation.resolve(filename);
				Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				return filePath.toString();
			} else {
				throw new FileNotFoundException("");
			}
		} catch (IOException | FileNotFoundException exception) {
			throw new FileNotFoundException(exception.getMessage());
		}
	}

	@Override
	public List<ProductPojo> addProduct(List<MultipartFile> files, String data) {
		try {
			List<Product> productList = mappper.readValue(data, new TypeReference<List<Product>>() {
			});
			dirLocation = getPath("Product");
			Files.createDirectories(dirLocation);
			for (MultipartFile multipartFile : files) {
				String originalFilename = multipartFile.getOriginalFilename();
				if (originalFilename == null)
					return Collections.emptyList();
				String fileName = addFile(multipartFile);
				for (Product product2 : productList) {
					String productName = product2.getProductName();
					if (productName.equalsIgnoreCase(originalFilename.substring(0, productName.length()))) {
						product2.setProductImage(fileName);
					}
				}
			}
			List<ProductPojo> productPojos = new ArrayList<>();
			List<Product> products = productList.stream().map(product -> {
				Product product2 = inventoryRepository.findByProductName(product.getProductName());
				if (product2 != null) {
					product.setProductId(product2.getProductId());
					product.setProductCount(product.getProductCount() + product2.getProductCount());
				}
				return product;
			}).collect(Collectors.toList());
			List<Product> productSave = inventoryRepository.saveAll(products);
			for (Product product2 : productSave) {
				productPojos.add(new ProductPojo(product2.getProductId(), product2.getProductName(),
						product2.getProductPrice(), product2.getProductCount(), product2.getProductImage()));
			}
			return productPojos;
		} catch (IOException | FileNotFoundException exception) {
			throw new FileNotFoundException(exception.getMessage());
		}
	}

	@Override
	public List<ProductPojo> fetchProduct() {
		List<Product> allProduct = inventoryRepository.findAll();
		List<ProductPojo> productPojos = new ArrayList<>();
		for (Product product2 : allProduct) {
			productPojos.add(new ProductPojo(product2.getProductId(), product2.getProductName(),
					product2.getProductPrice(), product2.getProductCount(), product2.getProductImage()));
		}
		return productPojos;
	}

	@Override
	public List<ProductPojo> fetchProductbyIdsAndSaveProduct(List<Integer> productIds, int productCount) {
		if (productIds.isEmpty())
			throw new NotFoundException("Product Id Is Empty!!!");
		List<Product> products = new ArrayList<>();
		List<ProductPojo> productPojos = new ArrayList<>();
		for (Integer productId : productIds) {
			Product product = inventoryRepository.findByProductId(productId);
			if (product != null) {
				int productCounts = product.getProductCount() - productCount;
				if (productCounts > 0) {
					productPojos.add(new ProductPojo(product.getProductId(), product.getProductName(),
							product.getProductPrice(), product.getProductCount(), product.getProductImage()));
					product.setProductCount(product.getProductCount() - productCount);
					products.add(product);
				} else
					throw new ProductOutOfStock("Product Out Of Stock!!!");
			}
		}
		if (!products.isEmpty())
			inventoryRepository.saveAll(products);
		return productPojos;
	}

	@Override
	public List<List<ProductPojo>> fetchProductbyIds(List<List<Integer>> productAllIds) {
		List<List<ProductPojo>> productAllPojos = new ArrayList<>();
		if (productAllIds.isEmpty())
			throw new NotFoundException("Product Id Is Empty!!!");
		for (List<Integer> productIds : productAllIds) {
			List<ProductPojo> productPojos = new ArrayList<>();
			for (Integer productId : productIds) {
				Product product = inventoryRepository.findByProductId(productId);
				if (product != null) {
					productPojos.add(new ProductPojo(product.getProductId(), product.getProductName(),
							product.getProductPrice(), product.getProductCount(), product.getProductImage()));
				}
			}
			productAllPojos.add(productPojos);
		}
		return productAllPojos;
	}

	@Override
	public List<ProductPojo> fetchAllProductbyIds(List<Integer> productIds) {

		
		
		if (productIds.isEmpty())
			throw new NotFoundException("Product Id Is Empty!!!");
		List<ProductPojo> productPojos = new ArrayList<>();
		for (Integer productId : productIds) {
			Product product = inventoryRepository.findByProductId(productId);
			if (product != null) {
				productPojos.add(new ProductPojo(product.getProductId(), product.getProductName(),
						product.getProductPrice(), product.getProductCount(), product.getProductImage()));
			}
		}
		return productPojos;
	}

}
