package com.zooplus.converter.service;

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
import com.zooplus.converter.repository.CryptoCurrencyRepository;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class CurrencyConverterServiceTest {

	@Mock
	CryptoCurrencyRepository cryptoCurrencyRepository;

	@InjectMocks
	CurrencyConverterService currencyConverterService;

	@Test
	void testGetAllCryptoCurrencies() {
		List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();
		cryptoCurrencies.add(new CryptoCurrency("BTC", "Bitcoin"));
		cryptoCurrencies.add(new CryptoCurrency("ETH", "Ether"));
		when(cryptoCurrencyRepository.findAll()).thenReturn(cryptoCurrencies);
		List<CryptoCurrency> cryptoCurrenciesActual = currencyConverterService.getAllCryptoCurrencies();
		Assertions.assertEquals(cryptoCurrencies, cryptoCurrenciesActual);
	}

}
