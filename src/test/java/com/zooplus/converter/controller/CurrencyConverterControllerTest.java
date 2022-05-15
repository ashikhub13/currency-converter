package com.zooplus.converter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.zooplus.converter.entity.Cryptocurrency;
import com.zooplus.converter.model.CryptoPriceRequest;
import com.zooplus.converter.service.CurrencyConverterService;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
class CurrencyConverterControllerTest {

	@InjectMocks
	CurrencyConverterController currencyConverterController;

	@Mock
	CurrencyConverterService currencyConverterService;

	@Mock
	private Model model;
	
	@Mock
	private Errors errors;

	@Test
	void testGetCryptoCurrencyList() {
		List<Cryptocurrency> cryptoCurrencies = new ArrayList<Cryptocurrency>();
		cryptoCurrencies.add(new Cryptocurrency("BTC", "Bitcoin"));
		cryptoCurrencies.add(new Cryptocurrency("ETH", "Ether"));
		when(currencyConverterService.getAllCryptoCurrencies()).thenReturn(cryptoCurrencies);
		Cryptocurrency cryptoCurrency = new Cryptocurrency(); 
		CryptoPriceRequest cryptoPriceRequest = new CryptoPriceRequest();
		String returnValue = currencyConverterController.getCryptoCurrencyList(cryptoCurrency, cryptoPriceRequest, model);
		verify(model, times(1)).addAttribute("cryptos", cryptoCurrencies);
        assertEquals("converter", returnValue);
	}

	@Test
	void testGetCryptoCurrencyWithValidCurrencyAndEmptyIP() {
		CryptoPriceRequest cryptoPriceRequest = new CryptoPriceRequest();
		cryptoPriceRequest.setCode("BTC");
		when(currencyConverterService.getPriceAndCurrency(null, "BTC")).thenReturn("€ 124.66");
		String returnValue = currencyConverterController.getCryptoCurrencyPrice(cryptoPriceRequest, errors, model);
		verify(model, times(1)).addAttribute("message", "€ 124.66");
		Assertions.assertEquals("converter", returnValue);
	}
	
	@Test
	void testGetCryptoCurrencyWithValidCurrencyAndIP() {
		CryptoPriceRequest cryptoPriceRequest = new CryptoPriceRequest();
		cryptoPriceRequest.setCode("BTC");
		cryptoPriceRequest.setAddress("85.214.132.117");
		when(currencyConverterService.getPriceAndCurrency("85.214.132.117", "BTC")).thenReturn("€ 124.66");
		String returnValue = currencyConverterController.getCryptoCurrencyPrice(cryptoPriceRequest, errors, model);
		verify(model, times(1)).addAttribute("message", "€ 124.66");
		Assertions.assertEquals("converter", returnValue);
	}

}
