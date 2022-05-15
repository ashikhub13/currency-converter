package com.zooplus.converter.dto;

/**
 * DTO for mapping data from price API *
 */
public class SpotPrice {
	private String base;
	private String currency;
	private String amount;

	// Getter Methods

	public String getAmount() {
		return amount;
	}

	// Setter Methods

	public void setBase(String base) {
		this.base = base;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}