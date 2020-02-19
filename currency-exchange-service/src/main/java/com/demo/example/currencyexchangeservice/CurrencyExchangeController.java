package com.demo.example.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.example.currencyexchangeservice.bean.ExchangeValue;
import com.demo.example.currencyexchangeservice.repository.CurrencyRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	Environment environment;
	
	@Autowired
	CurrencyRepository currencyRepository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchange = currencyRepository.findByFromAndTo(from, to);//new ExchangeValue(200L, from, to, BigDecimal.valueOf(65));
		exchange.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchange;
	}
}
