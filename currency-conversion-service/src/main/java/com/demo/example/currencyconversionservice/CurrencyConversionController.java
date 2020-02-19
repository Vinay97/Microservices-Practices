package com.demo.example.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.example.currencyconversionservice.bean.CurrencyConversionBean;


@RestController
public class CurrencyConversionController {
	
	@Autowired
	CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,uriVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(),response.getFrom(),response.getTo(),response.getConversionMultiple(),response.getConversionMultiple(),
				quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,uriVariables);
		CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);//responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(),response.getFrom(),response.getTo(),response.getConversionMultiple(),response.getConversionMultiple(),
				quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
}
