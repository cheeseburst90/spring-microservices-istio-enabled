package com.in28minutes.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment env;

	@GetMapping("/currency-converter/from/{from}/to/{to}/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		String currencyURI = System.getenv("GATEWAY_URL");
		logger.debug("forex_service_url from controller: " + currencyURI);
		if (StringUtils.isEmpty(currencyURI)) {
			System.out.println("spring env props: "+env.getProperty("something.url"));
			currencyURI = env.getProperty("something.url");
		}

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate()
				.getForEntity(currencyURI + "/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);

		CurrencyConversionBean response = responseEntity.getBody();

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}

}
