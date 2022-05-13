package com.zooplus.converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zooplus.converter.entity.CryptoCurrency;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, String> {}

