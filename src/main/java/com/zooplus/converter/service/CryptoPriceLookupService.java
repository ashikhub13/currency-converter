package com.zooplus.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.repository.CountryCurrencyRepository;
import com.zooplus.converter.util.PriceLookupUtil;

@Service
public class CryptoPriceLookupService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${price.url}")
	private String priceUrl;

	@Autowired
	CountryCurrencyRepository countryCurrencyRepository;

	public String getPrice(String cryptoCode, String curencyCode) {
		String price = "";
		try {
			String currency_pair = cryptoCode + "-" + curencyCode;
			String pairPriceUrl = priceUrl.replace("currency_pair", currency_pair);
			price = PriceLookupUtil.getCryptoPrice(restTemplate, pairPriceUrl);
		} catch (RestClientException e) {
			System.out.println("base-currency exception" + cryptoCode + "-" + curencyCode);
		}
		return price;
	}
}
