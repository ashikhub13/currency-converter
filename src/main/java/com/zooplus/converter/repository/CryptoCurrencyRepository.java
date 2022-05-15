package com.zooplus.converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zooplus.converter.entity.Cryptocurrency;

public interface CryptoCurrencyRepository extends JpaRepository<Cryptocurrency, String> {}

