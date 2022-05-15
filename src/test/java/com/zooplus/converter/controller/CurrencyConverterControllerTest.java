package com.zooplus.converter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.zooplus.converter.entity.CryptoCurrency;
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

	@Test
	void testGetCryptoCurrencyList() {
		List<CryptoCurrency> cryptoCurrencies = new ArrayList<CryptoCurrency>();
		cryptoCurrencies.add(new CryptoCurrency("BTC", "Bitcoin"));
		cryptoCurrencies.add(new CryptoCurrency("ETH", "Ether"));
		when(currencyConverterService.getAllCryptoCurrencies()).thenReturn(cryptoCurrencies);
		CryptoCurrency cryptoCurrency = new CryptoCurrency(); 
		CryptoPriceRequest cryptoPriceRequest = new CryptoPriceRequest();
		String returnValue = currencyConverterController.getCryptoCurrencyList(cryptoCurrency, cryptoPriceRequest, model);
		verify(model, times(1)).addAttribute("cryptos", cryptoCurrencies);
        assertEquals("converter", returnValue);
	}

	@Test
	void testGetCryptoCurrency() {
		CryptoPriceRequest cryptoPriceRequest = new CryptoPriceRequest();
		cryptoPriceRequest.setCode("BTC");
		when(currencyConverterService.getPriceAndCurrency(null, "BTC")).thenReturn("€ 124.66");
		String returnValue = currencyConverterController.getCryptoCurrencyPrice(cryptoPriceRequest, model);
		verify(model, times(1)).addAttribute("message", "€ 124.66");
		Assertions.assertEquals("converter", returnValue);
	}

}
