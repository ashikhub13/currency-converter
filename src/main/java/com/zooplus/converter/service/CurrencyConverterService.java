package com.zooplus.converter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zooplus.converter.entity.CryptoCurrency;
import com.zooplus.converter.repository.CryptoCurrencyRepository;
import com.zooplus.converter.util.DecimalFormatUtil;

@Service
public class CurrencyConverterService {

	@Autowired
	CryptoCurrencyRepository cryptoCurrencyRepository;

	@Autowired
	IPLookupService ipLookupService;

	@Autowired
	CryptoPriceLookupService cryptoPriceLookupService;

	public List<CryptoCurrency> getAllCryptoCurrencies() {
		return cryptoCurrencyRepository.findAll();
	}

	public String getPriceAndCurrency(String ipAddress, String cryptoCode) {
		String currencyCode = ipLookupService.getCurrencyCode(ipAddress);
		String price = cryptoPriceLookupService.getPrice(cryptoCode, currencyCode);
		java.util.Currency cur1 = java.util.Currency.getInstance(currencyCode);
		String symbol = cur1.getSymbol() != null ? cur1.getSymbol() : currencyCode;
		return symbol + " " + DecimalFormatUtil.trimDecimals(price);

	}

}
