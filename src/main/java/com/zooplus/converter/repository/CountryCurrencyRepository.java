package com.zooplus.converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zooplus.converter.entity.CountryCurrency;

public interface CountryCurrencyRepository extends JpaRepository<CountryCurrency, String> {}

