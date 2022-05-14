package com.zooplus.converter.controller;

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

import com.zooplus.converter.entity.CryptoCurrency;
import com.zooplus.converter.service.CurrencyConverterService;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
class CurrencyConverterControllerTest {

	@InjectMocks
	CurrencyConverterController currencyConverterController;

	@Mock
	CurrencyConverterService currencyConverterService;

	@Test
	void testGetCryptoCurrencyList() {
		List<CryptoCurrency> cryptoCurrencies = new ArrayList<CryptoCurrency>();
		cryptoCurrencies.add(new CryptoCurrency("BTC", "Bitcoin"));
		cryptoCurrencies.add(new CryptoCurrency("ETH", "Ether"));
		when(currencyConverterService.getAllCryptoCurrencies()).thenReturn(cryptoCurrencies);
		List<CryptoCurrency> cryptoCurrenciesActual = currencyConverterController.getCryptoCurrencyList();
		Assertions.assertEquals(cryptoCurrencies, cryptoCurrenciesActual);
	}

}
