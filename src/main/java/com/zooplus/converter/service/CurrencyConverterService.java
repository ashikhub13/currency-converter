package com.zooplus.converter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zooplus.converter.entity.CryptoCurrency;
import com.zooplus.converter.repository.CryptoCurrencyRepository;

@Service
public class CurrencyConverterService {

	@Autowired
	CryptoCurrencyRepository cryptoCurrencyRepository;

	public List<CryptoCurrency> getAllCryptoCurrencies() {
		return cryptoCurrencyRepository.findAll();
	}

}
