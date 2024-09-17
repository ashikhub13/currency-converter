package com.zooplus.converter.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.dto.SpotPriceResponse;

public class PriceLookupUtil {

	/**
	 * Function to return the price of the crypto coin
	 * @param restTemplate 
	 * 
	 * @param priceUrl
	 * @return
	 */
	public static String getCryptoPrice(RestTemplate restTemplate, String priceUrl) {
		ResponseEntity<SpotPriceResponse> spotPriceResponse = restTemplate.getForEntity(priceUrl,
				SpotPriceResponse.class);
		String price = spotPriceResponse.getBody().getData().getAmount();
		return price;
	}

}
