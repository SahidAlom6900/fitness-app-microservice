package com.technoelevate.user_service.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.technoelevate.user_service.entity.User;
import com.technoelevate.user_service.exception.CoachNotFoundException;
import com.technoelevate.user_service.exception.FallBackException;
import com.technoelevate.user_service.exception.NotFoundException;
import com.technoelevate.user_service.exception.NotFoundResponse;
import com.technoelevate.user_service.exception.UserNotFoundException;
import com.technoelevate.user_service.pojo.OrderPojo;
import com.technoelevate.user_service.pojo.OrderProductPojo;
import com.technoelevate.user_service.pojo.UserOrderPojo;
import com.technoelevate.user_service.pojo.UserPojo;
import com.technoelevate.user_service.pojo.UserRolePojo;
import com.technoelevate.user_service.repository.UserRepository;
import com.technoelevate.user_service.response.CoachResponse;
import com.technoelevate.user_service.response.PlanResponse;
import com.technoelevate.user_service.response.ResponseMessage;
import com.technoelevate.user_service.response.UserResponse;
import com.technoelevate.user_service.response.UserRoleResponse;

import static com.technoelevate.user_service.common.UserConstants.ADMIN_SERVICE;
import static com.technoelevate.user_service.common.UserConstants.ADMIN_SERVICE_FALLBACK_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.COACH_NOT_FOUND_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.NOT_FOUND_RESPONSE_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ORDER_SERVICE;
import static com.technoelevate.user_service.common.UserConstants.ORDER_SERVICE_FALLBACK_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.PRODUCT_ORDER_NOT_FOUND_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ROLE_SERVICE;
import static com.technoelevate.user_service.common.UserConstants.ROLE_SERVICE_FALLBACK_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.USER_NOT_FOUND_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.USER_PLAN_NOT_FOUND_MESSAGE;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Value("${steps.coin:1000}")
	private int stepsPerCoin;
	@Value("${rupees.coin:10}")
	private int rupeesPerCoin;

	private String logedinUsername = "Sahid Alom";

	@CircuitBreaker(name = ROLE_SERVICE, fallbackMethod = "roleServiceFallBack")
	@Override
	public UserPojo addUser(UserPojo userPojo) {
		String url = "http://role-service/api/v1/role/" + "USER";
		ResponseMessage responseMessage = restTemplate.getForObject(url, ResponseMessage.class);
		if (responseMessage == null)
			throw new NotFoundResponse(NOT_FOUND_RESPONSE_MESSAGE);
		Integer roleId = (Integer) responseMessage.getData();
		User user = new User();
		BeanUtils.copyProperties(userPojo, user);
		user.setRoleId(roleId);
		User user2 = userRepository.save(user);
		BeanUtils.copyProperties(user2, userPojo);
		return userPojo;
	}

	@CircuitBreaker(name = ADMIN_SERVICE, fallbackMethod = "roleServiceFallBack")
	@Override
	public UserPojo addCoach(Integer coachId) {
		String url = "http://admin-service/api/v1/admin/coach/" + coachId;
		ResponseMessage responseMessage = restTemplate.getForObject(url, ResponseMessage.class);
		if (responseMessage != null && responseMessage.isError())
			throw new NotFoundResponse(NOT_FOUND_RESPONSE_MESSAGE);
		User user = userRepository.findByUserName(logedinUsername);
		List<String> coach = user.getCoachId();
		List<String> coachs = new ArrayList<>();
		if (!coach.contains("")) {
			if (coach.contains(coachId.toString()))
				throw new CoachNotFoundException(COACH_NOT_FOUND_MESSAGE);
			coachs.addAll(coach);
		}
		coachs.add(coachId.toString());
		user.setCoachId(coachs);
		User user2 = userRepository.save(user);
		UserPojo userPojo = new UserPojo();
		BeanUtils.copyProperties(user2, userPojo);
		return userPojo;
	}

	@CircuitBreaker(name = ADMIN_SERVICE, fallbackMethod = "roleServiceFallBack")
	@Override
	public UserPojo addPlan(int planId) {
		String url = "http://admin-service/api/v1/admin/plan/" + planId;
		ResponseMessage responseMessage = restTemplate.getForObject(url, ResponseMessage.class);
		if (responseMessage != null && responseMessage.isError())
			throw new NotFoundResponse(NOT_FOUND_RESPONSE_MESSAGE);
		User user = userRepository.findByUserName(logedinUsername);
		if (user.getPlanId() > 0) {
			throw new NotFoundException(USER_PLAN_NOT_FOUND_MESSAGE);
		}
		user.setPlanId(planId);
		User user2 = userRepository.save(user);
		UserPojo userPojo = new UserPojo();
		BeanUtils.copyProperties(user2, userPojo);
		return userPojo;
	}

	@CircuitBreaker(name = ADMIN_SERVICE, fallbackMethod = "adminServiceFallBack")
	@Override
	public UserResponse getUser() {
		User user = userRepository.findByUserName(logedinUsername);
		if (user == null)
			throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
		if (user.getCoachId().contains(""))
			user.setCoachId(Collections.emptyList());
		log.info("Log is printed");
		String uriCoach = "http://admin-service/api/v1/admin/coachs/";
		HttpHeaders headers = new HttpHeaders();
		List<String> coachId = user.getCoachId();
		List<Integer> coachIds = coachId.stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
		HttpEntity<List<Integer>> httpEntity = new HttpEntity<>(coachIds, headers);
		ResponseEntity<CoachResponse> coachEntity = restTemplate.postForEntity(uriCoach, httpEntity,
				CoachResponse.class);
		String uriPlan = "http://admin-service/api/v1/admin/plan/" + user.getPlanId();
		ResponseEntity<PlanResponse> planEntity = restTemplate.getForEntity(uriPlan, PlanResponse.class);
		CoachResponse coachResponse = coachEntity.getBody();
		PlanResponse planResponse = planEntity.getBody();
		if (coachResponse == null || planResponse == null)
			throw new CoachNotFoundException(COACH_NOT_FOUND_MESSAGE);

		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(user, userResponse);
		userResponse.setCoachPojo(coachResponse.getData());
		userResponse.setPlanPojo(planResponse.getData());
		return userResponse;
	}

	@Override
	public UserOrderPojo getUserOrder() {
		User user = userRepository.findByUserName(logedinUsername);
		if (user == null)
			throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
		String uriOrder = "http://order-service/api/v1/order/" + user.getUserId();
		ResponseEntity<OrderProductPojo> userOrderEntity = getOrderProductPojo(uriOrder);
		OrderProductPojo orderProductPojo = userOrderEntity.getBody();
		if (orderProductPojo == null)
			throw new NotFoundException(PRODUCT_ORDER_NOT_FOUND_MESSAGE);
		UserOrderPojo userOrder = new UserOrderPojo();
		userOrder.setOrder(orderProductPojo.getData());
		BeanUtils.copyProperties(user, userOrder);
		return userOrder;
	}

	@CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "orderServiceFallBack")
	public ResponseEntity<OrderProductPojo> getOrderProductPojo(String uriOrder) {
		ResponseEntity<OrderProductPojo> responseEntity = restTemplate.getForEntity(uriOrder, OrderProductPojo.class);
		if (responseEntity.getBody().getData() == null)
			throw new FallBackException(ORDER_SERVICE_FALLBACK_MESSAGE);
		return responseEntity;
	}

	@Override
	public UserPojo addSteps(int steps) {
		User user = userRepository.findByUserName(logedinUsername);
		if (user == null)
			throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
		int coins = (user.getSteps() + steps) / stepsPerCoin;
		steps = (user.getSteps() + steps) % stepsPerCoin;
		user.setCoins(user.getCoins() + coins);
		user.setSteps(steps);
		User user2 = userRepository.save(user);
		UserPojo userPojo = new UserPojo();
		BeanUtils.copyProperties(user2, userPojo);
		return userPojo;
	}

	@Override
	public UserPojo addOrderToUserByCoin(OrderPojo orderPojo) {
		User user = userRepository.findByUserName(logedinUsername);
		int coins = user.getCoins();
		double avialablePrice = 0;
		double coinPrice = 0;
		UserPojo userPojo = new UserPojo();
		if (coins > 0) {
			coinPrice = coins * rupeesPerCoin;
			if (orderPojo.getTotalPrice() - coinPrice > 0) {
				avialablePrice = orderPojo.getTotalPrice() - coinPrice;
				user.setCoins(0);
			} else {
				avialablePrice = 0;
				coinPrice = coinPrice - orderPojo.getTotalPrice();
				user.setCoins((int) (coinPrice) / rupeesPerCoin);
				user.setSteps((int) (((coinPrice) % rupeesPerCoin) / rupeesPerCoin * stepsPerCoin) + user.getSteps());
			}
			userPojo.setTotalPrice(avialablePrice);
			userRepository.save(user);
		} else
			userPojo.setTotalPrice(orderPojo.getTotalPrice());
		userPojo.setUserId(user.getUserId());
		return userPojo;
	}

	public UserPojo roleServiceFallBack(Exception exception) {
		throw new FallBackException(ROLE_SERVICE_FALLBACK_MESSAGE);
	}

	public UserOrderPojo orderServiceFallBack(Exception exception) {
		throw new FallBackException(ORDER_SERVICE_FALLBACK_MESSAGE);
	}

	public UserResponse adminServiceFallBack(Exception exception) {
		throw new FallBackException(ADMIN_SERVICE_FALLBACK_MESSAGE);
	}

	@Override
	public UserRolePojo getUser(String userName) {
		User user = userRepository.findByUserName(userName);
		String url = "http://role-service/api/v1/role/" + user.getRoleId();
		UserRoleResponse responseMessage = restTemplate.postForObject(url,null, UserRoleResponse.class);
		if (responseMessage == null)
			throw new NotFoundResponse(NOT_FOUND_RESPONSE_MESSAGE);
		UserRolePojo userRolePojo=new UserRolePojo();
		userRolePojo.setUserName(user.getUserName());
		userRolePojo.setPassword(user.getPassword());
		userRolePojo.setRolePojo(responseMessage.getData());
		return userRolePojo;
	}

}
