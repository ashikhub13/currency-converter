package com.zooplus.converter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.zooplus.converter.entity.CryptoCurrency;
import com.zooplus.converter.model.CryptoPriceRequest;
import com.zooplus.converter.service.CurrencyConverterService;

@Controller
public class CurrencyConverterController {

	@Autowired
	CurrencyConverterService currencyConverterService;

	/**
	 * Get list of all available cryptocurrencies
	 */
	@GetMapping("/crypto")
	public String getCryptoCurrencyList(@ModelAttribute("cryptocurrency") CryptoCurrency cryptocurrency, @ModelAttribute("cryptoPriceRequest") CryptoPriceRequest cryptoPriceRequest, Model model) {
		model.addAttribute("cryptos", currencyConverterService.getAllCryptoCurrencies());
		return "converter";
	}

	/**
	 * Get localized price of a cryptocurrency
	 */
	@PostMapping("/crypto")
	public String getCryptoCurrencyPrice( @Valid CryptoPriceRequest cryptoPriceRequest,  Model model) {
		String ipAddress = cryptoPriceRequest.getAddress();
		String cryptoCode = cryptoPriceRequest.getCode();
		String price = currencyConverterService.getPriceAndCurrency(ipAddress, cryptoCode);
		model.addAttribute("message", price);
		model.addAttribute("selectedCrypto", cryptoCode);
		model.addAttribute("cryptos", currencyConverterService.getAllCryptoCurrencies());
		return "converter";
	}

}
