package com.in28minutes.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/static-filtering")
	public SomeBean staticFiltering() {
		return new SomeBean("Val1", "Val2", "Val3");
	}
	
	@GetMapping("/static-filtering-list")
	public List<SomeBean> staticFilteringList() {
		return Arrays.asList(new SomeBean("Val1", "Val2", "Val3"),
				new SomeBean("Val4", "Val5", "Val6"));	 
	}
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		
		//MappingJacksonValue -- To do dynamic filtering
		SomeBean somebean = new SomeBean("Val1", "Val2", "Val3");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(somebean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
		// So basically for dynamic filering, the below is used. First define the name 'SomeBeanFilter' in the bean class as @JsonFilter("SomeBeanFilter")
		// second mention / create filter
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);	
		return mappingJacksonValue;
	}
	
	 // dynamic filter field 2 & 3
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List beanList = Arrays.asList(new SomeBean("Val1", "Val2", "Val3"),
				new SomeBean("Val4", "Val5", "Val6"));

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(beanList);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
		mappingJacksonValue.setFilters(filters);

		return mappingJacksonValue;
		
	}
	
	

}
