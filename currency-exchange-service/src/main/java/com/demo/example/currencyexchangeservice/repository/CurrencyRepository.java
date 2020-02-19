package com.demo.example.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.example.currencyexchangeservice.bean.ExchangeValue;

@Repository
public interface CurrencyRepository extends JpaRepository<ExchangeValue, Long>{

	ExchangeValue findByFromAndTo(String from, String to);
}
