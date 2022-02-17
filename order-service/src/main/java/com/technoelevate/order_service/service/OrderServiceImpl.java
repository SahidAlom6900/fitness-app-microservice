package com.technoelevate.order_service.service;

import static com.technoelevate.order_service.common.OrderConstants.INVENTORY_SERVICE;
import static com.technoelevate.order_service.common.OrderConstants.INVENTORY_SERVICE_FALLBACK_MESSAGE;
import static com.technoelevate.order_service.common.OrderConstants.ORDER_SERVICE;
import static com.technoelevate.order_service.common.OrderConstants.PAYMENT_SERVICE;
import static com.technoelevate.order_service.common.OrderConstants.PAYMENT_SERVICE_FALLBACK_MESSAGE;
import static com.technoelevate.order_service.common.OrderConstants.USER_NOT_FOUND_MESSAGE;
import static com.technoelevate.order_service.common.OrderConstants.USER_SERVICE;
import static com.technoelevate.order_service.common.OrderConstants.USER_SERVICE_FALLBACK_MESSAGE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.technoelevate.order_service.entity.Order;
import com.technoelevate.order_service.exception.FallBackException;
import com.technoelevate.order_service.exception.UserNotFoundException;
import com.technoelevate.order_service.pojo.AccountPojo;
import com.technoelevate.order_service.pojo.OrderPojo;
import com.technoelevate.order_service.pojo.ProductPojo;
import com.technoelevate.order_service.pojo.UserOrderPojo;
import com.technoelevate.order_service.repository.OrderRepository;
import com.technoelevate.order_service.response.InventoryResponse;
import com.technoelevate.order_service.response.InventoryResponseDemo;
import com.technoelevate.order_service.response.ResponseMessage;
import com.technoelevate.order_service.response.UserResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestTemplate restTemplate;

	private double totalPrice;
	private String uriProduct = "http://inventory-service/api/v1/product/order";
	private String uriUser = "http://user-service/api/v1/user";
	private String uriUserCoin = "http://user-service/api/v1/user/coin";
	private String uriAccount = "http://payment-service/api/v1/account/retrive";
	
	
//	@Autowired
//    private WebClient.Builder webClientBuilder;

//    public Mono<String> createIssue(String field) {
//        return webClientBuilder.build()
//                .post()
//                .uri("/rest/api/")
//                .body(Mono.just(field), Fields.class)
//                .retrieve()
//                .bodyToMono(String.class);
//    }}

	@CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "orderFallback")
	@Override
	public OrderPojo addOrderToUser(OrderPojo orderPojo) {
		HttpHeaders headers = new HttpHeaders();
		List<String> productId = orderPojo.getProductId();
		List<Integer> productIds = productId.stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
		HttpEntity<List<Integer>> request = new HttpEntity<>(productIds, headers);
		ResponseEntity<InventoryResponse> inventoryEntity = getInventoryResponse(orderPojo, request);
		InventoryResponse inventoryResponse = inventoryEntity.getBody();
		if (inventoryResponse == null)
			throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
		totalPrice = 0;
		inventoryResponse.getData().stream()
				.forEach(product -> totalPrice += product.getProductPrice() * orderPojo.getProductCount());

		orderPojo.setOrderDate(LocalDate.now());
		orderPojo.setTotalPrice(totalPrice);

		ResponseEntity<UserResponse> userEntity = getUserResponse(orderPojo, headers);

		UserResponse userResponse = userEntity.getBody();
		if (userResponse == null)
			throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);

		if (userResponse.getData().getTotalPrice() > 0) {
			AccountPojo accountPojo = new AccountPojo(13988830026l, totalPrice);
			HttpEntity<AccountPojo> httpEntity = new HttpEntity<>(accountPojo, headers);
			ResponseEntity<ResponseMessage> responseMessage = getPaymentResponse(httpEntity);
			ResponseMessage message = responseMessage.getBody();
			if (message != null && message.isError()) {
				orderPojo.setStatus("FAILED");
				return orderPojo;
			}
		}
		orderPojo.setUserId(userResponse.getData().getUserId());
		orderPojo.setStatus("CONFIRMED");
		Order order = new Order();
		BeanUtils.copyProperties(orderPojo, order);
		Order order2 = orderRepository.save(order);
		BeanUtils.copyProperties(order2, orderPojo);
		return orderPojo;
	}

	@CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "inventoryFallbackResponse")
	private ResponseEntity<InventoryResponse> getInventoryResponse(OrderPojo orderPojo,
			HttpEntity<List<Integer>> request) {
		return restTemplate.postForEntity(uriProduct + "/" + orderPojo.getProductCount(), request,
				InventoryResponse.class);
	}

	@CircuitBreaker(name = USER_SERVICE, fallbackMethod = "userFallback")
	private ResponseEntity<UserResponse> getUserResponse(OrderPojo orderPojo, HttpHeaders headers) {
		ResponseEntity<UserResponse> userEntity = null;
		if (orderPojo.isAddCoin()) {
			HttpEntity<OrderPojo> postRequest = new HttpEntity<>(orderPojo, headers);
			userEntity = restTemplate.postForEntity(uriUserCoin, postRequest, UserResponse.class);
		} else {
			userEntity = restTemplate.getForEntity(uriUser, UserResponse.class);
		}
		return userEntity;
	}

	@CircuitBreaker(name = PAYMENT_SERVICE, fallbackMethod = "paymentFallback")
	private ResponseEntity<ResponseMessage> getPaymentResponse(HttpEntity<AccountPojo> httpEntity) {
		return restTemplate.postForEntity(uriAccount, httpEntity, ResponseMessage.class);
	}

	@CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "inventoryFallback")
	@Override
	public List<UserOrderPojo> getUserOrder(int userId) {
		try {
			int index = 0;
			List<Order> orders = orderRepository.findByUserId(userId);
			List<List<Integer>> productAllIds = new ArrayList<>();
			List<UserOrderPojo> userOrderPojos = new ArrayList<>();
			orders.stream().forEach(order -> {
				List<Integer> productIds = order.getProductId().stream().map(id -> Integer.parseInt(id))
						.collect(Collectors.toList());
				productAllIds.add(productIds);
			});
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<List<List<Integer>>> request = new HttpEntity<>(productAllIds, headers);
			ResponseEntity<InventoryResponseDemo> inventoryEntity = restTemplate.postForEntity(uriProduct, request,
					InventoryResponseDemo.class);
			List<List<ProductPojo>> products = inventoryEntity.getBody().getData();
			for (List<ProductPojo> list : products) {
				UserOrderPojo userOrderPojo = new UserOrderPojo();
				BeanUtils.copyProperties(orders.get(index++), userOrderPojo);
				userOrderPojo.setProductDetails(list);
				userOrderPojos.add(userOrderPojo);
			}
			return userOrderPojos;
		}  catch (Exception e) {
			throw e;
		}
	}
	
	

	@CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "inventoryFallback")
	@Override
	public List<UserOrderPojo> getUserOrderDemo(int userId) {
		List<Order> orders = orderRepository.findByUserId(userId);
		List<UserOrderPojo> userOrderPojos = new ArrayList<>();
		orders.stream().forEach(order -> {
			UserOrderPojo userOrderPojo = new UserOrderPojo();
			List<Integer> productIds = order.getProductId().stream().map(id -> Integer.parseInt(id))
					.collect(Collectors.toList());
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<List<Integer>> request = new HttpEntity<>(productIds, headers);
			ResponseEntity<InventoryResponse> inventoryEntity = restTemplate.postForEntity(uriProduct + "s", request,
					InventoryResponse.class);
			InventoryResponse inventoryResponse = inventoryEntity.getBody();

			BeanUtils.copyProperties(order, userOrderPojo);
			userOrderPojo.setProductDetails(inventoryResponse.getData());
			userOrderPojos.add(userOrderPojo);
		});
		return userOrderPojos;
	}

	public List<UserOrderPojo> inventoryFallback(Exception exception) {
		throw new FallBackException(INVENTORY_SERVICE_FALLBACK_MESSAGE);
	}

	public ResponseEntity<InventoryResponse> inventoryFallbackResponse(Exception exception) {
		throw new FallBackException(INVENTORY_SERVICE_FALLBACK_MESSAGE);
	}

	public ResponseEntity<UserResponse> userFallback(Exception exception) {
		throw new FallBackException(USER_SERVICE_FALLBACK_MESSAGE);
	}

	public ResponseEntity<ResponseMessage> paymentFallback(Exception exception) {
		throw new FallBackException(PAYMENT_SERVICE_FALLBACK_MESSAGE);
	}
	public OrderPojo orderFallback(Exception exception) {
		throw new FallBackException(PAYMENT_SERVICE_FALLBACK_MESSAGE);
	}

}
