package com.technoelevate.user_service.service;

import com.technoelevate.user_service.pojo.OrderPojo;
import com.technoelevate.user_service.pojo.UserOrderPojo;
import com.technoelevate.user_service.pojo.UserPojo;
import com.technoelevate.user_service.pojo.UserRolePojo;
import com.technoelevate.user_service.response.UserResponse;

public interface UserService {
	UserPojo addUser(UserPojo userPojo);

	UserPojo addCoach(Integer coachId);

	UserPojo addPlan(int planId);

	UserResponse getUser();

	UserRolePojo getUser(String userName);

	UserOrderPojo getUserOrder();

	UserPojo addSteps(int steps);

	UserPojo addOrderToUserByCoin(OrderPojo orderPojo);
}
