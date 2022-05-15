package com.zooplus.converter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zooplus.converter.entity.CryptoCurrency;
import com.zooplus.converter.model.CryptoPriceRequest;
import com.zooplus.converter.service.CurrencyConverterService;

@RestController
public class CurrencyConverterController {

	@Autowired
	CurrencyConverterService currencyConverterService;

	/**
	 * Get list of all available cryptocurrencies
	 */
	@GetMapping("/crypto")
	public List<CryptoCurrency> getCryptoCurrencyList() {
		return currencyConverterService.getAllCryptoCurrencies();
	}

	/**
	 * Borrow a book from library by passing the id of a book
	 */
	@PostMapping("/crypto")
	public String getCryptoCurrencyPrice(@RequestBody CryptoPriceRequest cryptoPriceRequest) {
		String ipAddress = cryptoPriceRequest.getAddress();
		String cryptoCode = cryptoPriceRequest.getCode();
		String price = currencyConverterService.getPriceAndCurrency(ipAddress, cryptoCode);
		return price;
	}

}
