package com.technoelevate.admin.common;

public class AdminConstants {
	// resilience4j Message begin
		public static final String ROLE_SERVICE_FALLBACK_MESSAGE = "Role Service is taking too long to respond or is down. Please try again later!!!";
		public static final String PAYMENT_SERVICE_FALLBACK_MESSAGE = "Payment Service is taking too long to respond or is down. Please try again later!!!";
		public static final String ORDER_SERVICE_FALLBACK_MESSAGE = "Order Service is taking too long to respond or is down. Please try again later!!!";
		public static final String ADMIN_SERVICE_FALLBACK_MESSAGE = "Admin Service is taking too long to respond or is down. Please try again later!!!";
		public static final String USER_SERVICE_FALLBACK_MESSAGE = "User Service is taking too long to respond or is down. Please try again later!!!";
		public static final String INVENTORY_SERVICE_FALLBACK_MESSAGE = "Inventory Service is taking too long to respond or is down. Please try again later!!!";
		// resilience4j Message end

		// Circuit Breaker Name Begin
		public static final String ROLE_SERVICE = "role_service";
		public static final String PAYMENT_SERVICE = "payment_service";
		public static final String ADMIN_SERVICE = "admin_service";
		public static final String USER_SERVICE = "user_service";
		public static final String ORDER_SERVICE = "order_service";
		public static final String INVENTORY_SERVICE = "inventory_service";
		// Circuit Breaker Name Begin
}
