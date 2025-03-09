package com.in28minutes.microservices.currency_conversion_service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariable = new HashMap<String, String>();
		uriVariable.put("from", from);
		uriVariable.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
			("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
					CurrencyConversion.class,uriVariable);
		
		CurrencyConversion currencyConv = responseEntity.getBody();
		
		return new CurrencyConversion(currencyConv.getId(), from, to, 
				quantity, currencyConv.getConversionMultiple(), 
				quantity.multiply(currencyConv.getConversionMultiple()), currencyConv.getEnvironment() + " Rest template" );
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConv = proxy.retriveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConv.getId(), from, to, 
				quantity, currencyConv.getConversionMultiple(), 
				quantity.multiply(currencyConv.getConversionMultiple()), currencyConv.getEnvironment() + " Feign" );
	}

}
