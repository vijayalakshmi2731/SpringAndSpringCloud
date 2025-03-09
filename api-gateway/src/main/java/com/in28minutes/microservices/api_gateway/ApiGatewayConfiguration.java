package com.in28minutes.microservices.api_gateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*Here as well we are utilizing/chaning the functionality of API gateway.
 * RouteLocator - RouteLocatorBuilder builder (this is main)
 * so the builder will have routes().route(...
 *  
 */
@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
//		Function<PredicateSpec, Buildable<Route>> routeFunction 
//		= p -> p.path("/get")
//				.filters(f -> f
//						.addRequestHeader("MyHeader", "MyURI")
//						.addRequestParameter("Param", "MyValuse"))
//				.uri("http://httpbin.org:80");
		
	/*	- http://localhost:8765/currency-exchange-service/currency-exchange/from/USD/to/INR (Instead of this we can custom route to this by calling the below)
		  http://localhost:8765/currency-exchange/from/USD/to/INR

		- http://localhost:8765/currency-conversion-service/currency-conversion/from/USD/to/INR/quantity/10

		- http://localhost:8765/currency-conversion-service/currency-conversion-feign/from/USD/to/INR/quantity/10	*/	
		
		
		return builder.routes().route(p -> p.path("/get")
				.filters(f -> f
						.addRequestHeader("MyHeader", "MyURI")
						.addRequestParameter("Param", "MyValuse"))
				.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange-service"))
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion-service"))
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion-service"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion-service"))
				.build();
	}

}
