package com.zooplus.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.dto.SpotPrice;
import com.zooplus.converter.dto.SpotPriceResponse;

@SpringBootTest
class PriceLookupUtilTest {

	@Mock
	RestTemplate restTemplate;

	@Test
	void testGetCryptoPrice() {
		String priceUrl = "https://api.coinbase.com/v2/prices/BTC_EUR/spot";
		SpotPriceResponse spotPriceResponse = new SpotPriceResponse();
		SpotPrice spotPrice = new SpotPrice();
		spotPrice.setCurrency("EUR");
		spotPrice.setBase("BTC");
		spotPrice.setAmount("20.00");
		spotPriceResponse.setData(spotPrice);
		Mockito.when(restTemplate.getForEntity(priceUrl, SpotPriceResponse.class))
				.thenReturn(new ResponseEntity<SpotPriceResponse>(spotPriceResponse, HttpStatus.OK));
		assertEquals("20.00", PriceLookupUtil.getCryptoPrice(restTemplate, priceUrl));
	}

}
