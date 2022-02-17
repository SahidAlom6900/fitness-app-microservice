package com.technoelevate.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.user_service.pojo.OrderPojo;
import com.technoelevate.user_service.pojo.UserOrderPojo;
import com.technoelevate.user_service.pojo.UserPojo;
import com.technoelevate.user_service.pojo.UserRolePojo;
import com.technoelevate.user_service.response.ResponseMessage;
import com.technoelevate.user_service.response.UserResponse;
import com.technoelevate.user_service.service.UserService;

import static com.technoelevate.user_service.common.UserConstants.ADD_COACH_SUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_COACH_UNSUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_PLAN_SUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_PLAN_UNSUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_STEPS_SUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_STEPS_UNSUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_USER_SUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.ADD_USER_UNSUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.FETCH_USER_ORDER_SUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.FETCH_USER_ORDER_UNSUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.FETCH_USER_SUCCESS_MESSAGE;
import static com.technoelevate.user_service.common.UserConstants.FETCH_USER_UNSUCCESS_MESSAGE;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<ResponseMessage> addUser(@RequestBody UserPojo userPojo) {
		UserPojo userPojo2 = userService.addUser(userPojo);
		if (userPojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, ADD_USER_SUCCESS_MESSAGE, userPojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, ADD_USER_UNSUCCESS_MESSAGE, userPojo2), HttpStatus.OK);
	}

	@PostMapping("coach/{coachId}")
	public ResponseEntity<ResponseMessage> addCoachToUser(@PathVariable("coachId") int coachId) {
		UserPojo userPojo2 = userService.addCoach(coachId);
		if (userPojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, ADD_COACH_SUCCESS_MESSAGE, userPojo2),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, ADD_COACH_UNSUCCESS_MESSAGE, userPojo2),
				HttpStatus.BAD_REQUEST);
	}

	@PostMapping("plan/{planId}")
	public ResponseEntity<ResponseMessage> addPlanToUser(@PathVariable("planId") int planId) {
		UserPojo userPojo2 = userService.addPlan(planId);
		if (userPojo2 != null)
			return new ResponseEntity<>(new ResponseMessage(false, ADD_PLAN_SUCCESS_MESSAGE, userPojo2), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, ADD_PLAN_UNSUCCESS_MESSAGE, userPojo2),
				HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	public ResponseEntity<ResponseMessage> getUser() {
		UserResponse userResponse = userService.getUser();
		if (userResponse != null)
			return new ResponseEntity<>(new ResponseMessage(false, FETCH_USER_SUCCESS_MESSAGE, userResponse),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, FETCH_USER_UNSUCCESS_MESSAGE, userResponse),
				HttpStatus.OK);
	}

	@PostMapping("/role/{userName}")
	public ResponseEntity<ResponseMessage> getUser(@PathVariable("userName") String userName) {
		UserRolePojo response = userService.getUser(userName);
		if (response != null)
			return new ResponseEntity<>(new ResponseMessage(false, FETCH_USER_SUCCESS_MESSAGE, response),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, FETCH_USER_UNSUCCESS_MESSAGE, response), HttpStatus.OK);
	}

	@GetMapping("order")
	public ResponseEntity<ResponseMessage> getUserOrder() {
		UserOrderPojo userOrder = userService.getUserOrder();
		if (userOrder != null)
			return new ResponseEntity<>(new ResponseMessage(false, FETCH_USER_ORDER_SUCCESS_MESSAGE, userOrder),
					HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, FETCH_USER_ORDER_UNSUCCESS_MESSAGE, userOrder),
				HttpStatus.BAD_REQUEST);
	}

	@PostMapping("user-step/{steps}")
	public ResponseEntity<ResponseMessage> addStepsToUser(@PathVariable("steps") int steps,
			@CookieValue(name = "user-id", defaultValue = "default-user-id") String userId) {

		System.out.println(userId);

		UserPojo user = userService.addSteps(steps);
		if (user != null)
			return new ResponseEntity<>(new ResponseMessage(false, ADD_STEPS_SUCCESS_MESSAGE, user), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, ADD_STEPS_UNSUCCESS_MESSAGE, user), HttpStatus.OK);
	}// end addStepsToUser method

	@PostMapping("coin")
	public ResponseEntity<ResponseMessage> addOrderToUserByCoin(@RequestBody OrderPojo orderPojo) {
		UserPojo user = userService.addOrderToUserByCoin(orderPojo);
		if (user != null)
			return new ResponseEntity<>(new ResponseMessage(false, ADD_STEPS_SUCCESS_MESSAGE, user), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseMessage(true, ADD_STEPS_UNSUCCESS_MESSAGE, user), HttpStatus.OK);
	}// end addOrderToUserByCoin method

//	public ResponseEntity<ResponseMessage> userServiceFallBack(Exception exception) {
//		return new ResponseEntity<>(
//				new ResponseMessage(true,
//						"User Service is taking too long to respond or is down. Please try again later", null),
//				HttpStatus.OK);
//
//	}
}
