package com.yy.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	/**
	 * Create a bean of type route locator, we are going to send all
	 * custom routing requirements.
	 * @param routeLocatorBuilder
	 * @return
	 */
	@Bean
	public RouteLocator yyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {

		return routeLocatorBuilder.routes()
				.route(p -> p.path("/yyBank/accounts/**")
						.filters(f -> f.rewritePath("/yyBank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p.path("/yyBank/loans/**")
						.filters(f -> f.rewritePath("/yyBank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p.path("/yyBank/cards/**")
						.filters(f -> f.rewritePath("/yyBank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}
}
