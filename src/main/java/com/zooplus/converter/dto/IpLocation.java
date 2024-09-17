package com.zooplus.converter.dto;

/**
 * DTO for mapping IPLocation from external API *
 */
public class IpLocation {
	private String ip;
	private String ip_number;
	private float ip_version;
	private String country_name;
	private String country_code2;
	private String isp;
	private String response_code;
	private String response_message;

	// Getter Methods

	public String getCountry_code2() {
		return country_code2;
	}

	// Setter Methods

	public void setCountry_code2(String country_code2) {
		this.country_code2 = country_code2;
	}

}
