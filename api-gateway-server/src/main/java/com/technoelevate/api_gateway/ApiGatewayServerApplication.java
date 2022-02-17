package com.technoelevate.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServerApplication.class, args);
	}

//	@SuppressWarnings("deprecation")
//	@Bean
//	public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
//			CircuitBreakerRegistry circuitBreakerRegistry) {
//		ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory();
//		reactiveResilience4JCircuitBreakerFactory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
//
//		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(100))
//				.cancelRunningFuture(true).build();
//		reactiveResilience4JCircuitBreakerFactory.configure(
//				builder -> builder.timeLimiterConfig(timeLimiterConfig).build(), "user-service", "role-service",
//				"admin-service", "payment-service", "inventory-service", "order-service");
//		return reactiveResilience4JCircuitBreakerFactory;
//	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	public ServerCodecConfigurer serverCodecConfigurer() {
//	   return ServerCodecConfigurer.create();
//	}
	
}
