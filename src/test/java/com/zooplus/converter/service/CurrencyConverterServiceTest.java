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

import com.zooplus.converter.entity.Cryptocurrency;
import com.zooplus.converter.exception.CountryCurrencyLookupException;
import com.zooplus.converter.exception.IPMetadataLookupException;
import com.zooplus.converter.exception.IPNotFoundException;
import com.zooplus.converter.repository.CryptoCurrencyRepository;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class CurrencyConverterServiceTest {

	@Mock
	CryptoCurrencyRepository cryptoCurrencyRepository;

	@InjectMocks
	CurrencyConverterService currencyConverterService;

	@Mock
	IPLookupService ipLookupService;

	@Mock
	CryptoPriceLookupService cryptoPriceLookupService;

	@Test
	void testGetAllCryptoCurrencies() {
		List<Cryptocurrency> cryptoCurrencies = new ArrayList<>();
		cryptoCurrencies.add(new Cryptocurrency("BTC", "Bitcoin"));
		cryptoCurrencies.add(new Cryptocurrency("ETH", "Ether"));
		when(cryptoCurrencyRepository.findAll()).thenReturn(cryptoCurrencies);
		List<Cryptocurrency> cryptoCurrenciesActual = currencyConverterService.getAllCryptoCurrencies();
		Assertions.assertEquals(cryptoCurrencies, cryptoCurrenciesActual);
	}

	@Test
	void testGetPriceAndCurrency() {
		String ipAddress = "85.214.132.117";
		String cryptoCode = "BTC";
		String price = "124.66";
		when(ipLookupService.getCurrencyCode(ipAddress)).thenReturn("EUR");
		when(cryptoPriceLookupService.getPrice("BTC", "EUR")).thenReturn(price);
		String priceActual = currencyConverterService.getPriceAndCurrency(ipAddress, cryptoCode);
		Assertions.assertEquals("€ 124.66", priceActual);
	}

	@Test
	void testGetPriceAndCurrency_expect_IPNotFoundException() {
		String ipAddress = "85.214.132.117";
		String cryptoCode = "BTC";
		when(ipLookupService.getCurrencyCode(ipAddress)).thenReturn("EUR");
		when(cryptoPriceLookupService.getPrice("BTC", "EUR")).thenThrow(IPNotFoundException.class);
		Assertions.assertThrows(IPNotFoundException.class, () -> {
			currencyConverterService.getPriceAndCurrency(ipAddress, cryptoCode);
		});
	}

	@Test
	void testGetPriceAndCurrency_expect_IPMetadataLookupException() {
		String ipAddress = "85.214.132.117";
		String cryptoCode = "BTC";
		String price = "124.66";
		when(ipLookupService.getCurrencyCode(ipAddress)).thenReturn("EUR");
		when(cryptoPriceLookupService.getPrice("BTC", "EUR")).thenThrow(IPMetadataLookupException.class);
		Assertions.assertThrows(IPMetadataLookupException.class, () -> {
			currencyConverterService.getPriceAndCurrency(ipAddress, cryptoCode);
		});
	}

	@Test
	void testGetPriceAndCurrency_expect_CountryCurrencyLookupException() {
		String ipAddress = "85.214.132.117";
		String cryptoCode = "BTC";
		when(ipLookupService.getCurrencyCode(ipAddress)).thenReturn("EUR");
		when(cryptoPriceLookupService.getPrice("BTC", "EUR")).thenThrow(CountryCurrencyLookupException.class);
		Assertions.assertThrows(CountryCurrencyLookupException.class, () -> {
			currencyConverterService.getPriceAndCurrency(ipAddress, cryptoCode);
		});
	}
}
