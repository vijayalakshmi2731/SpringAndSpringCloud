package com.in28minutes.microservices.currency_conversion_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange-service",url ="localhost:8000")
// The below will do load balancing dynamically with eureka
@FeignClient(name="currency-exchange-service")
public interface CurrencyExchangeProxy {
	
	//CurrencyConversion matching the response of the retriveExchangeValue 
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retriveExchangeValue(@PathVariable String from, @PathVariable String to);

}
 