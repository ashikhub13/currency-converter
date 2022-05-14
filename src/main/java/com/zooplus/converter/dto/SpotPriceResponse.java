package com.zooplus.converter.dto;

/**
 * DTO for mapping price data from price API *
 */
public class SpotPriceResponse {
	SpotPrice data;

	// Getter Methods

	public SpotPrice getData() {
		return data;
	}

	// Setter Methods

	public void setData(SpotPrice data) {
		this.data = data;
	}
}
