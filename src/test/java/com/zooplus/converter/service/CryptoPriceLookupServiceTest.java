package com.zooplus.converter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.util.DecimalFormatUtil;
import com.zooplus.converter.util.PriceLookupUtil;

@SpringBootTest
public class CryptoPriceLookupServiceTest {

	@InjectMocks
	CryptoPriceLookupService cryptoPriceLookupService;

	@Mock
	RestTemplate restTemplate;

	@Test
	void testGetPrice() {
		String cryptoCode = "BTC";
		String curencyCode = "USD";
		String price = "124.66";
		String priceUrl = "https://api.coinbase.com/v2/prices/currency_pair/spot";
		String currency_pair = cryptoCode + "-" + curencyCode;
		String pairPriceUrl = priceUrl.replace("currency_pair", currency_pair);
		ReflectionTestUtils.setField(cryptoPriceLookupService, "priceUrl", priceUrl);
		try (MockedStatic<PriceLookupUtil> priceLookupUtil = Mockito.mockStatic(PriceLookupUtil.class)) {
			priceLookupUtil.when(() -> PriceLookupUtil.getCryptoPrice(restTemplate, pairPriceUrl)).thenReturn(price);
			assertEquals(price, cryptoPriceLookupService.getPrice(cryptoCode, curencyCode));
		}
	}
}
