package com.in28minutes.springboot.microservice.example.forex.springbootmicroserviceforexservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForexController {

	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepository repository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		System.out.println("inside method:");
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		System.out.println("exchangeValue generated: " + exchangeValue);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
}