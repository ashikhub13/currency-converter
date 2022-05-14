package com.zooplus.converter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zooplus.converter.entity.CryptoCurrency;
import com.zooplus.converter.service.CurrencyConverterService;

@RestController
public class CurrencyConverterController {

	@Autowired
	CurrencyConverterService currencyConverterService;

	@GetMapping("view")
	public List<CryptoCurrency> getCryptoCurrencyList() {
		return currencyConverterService.getAllCryptoCurrencies();
	}

}
