package com.zooplus.converter.dto;

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

	public String getIp() {
		return ip;
	}

	public String getIp_number() {
		return ip_number;
	}

	public float getIp_version() {
		return ip_version;
	}

	public String getCountry_name() {
		return country_name;
	}

	public String getCountry_code2() {
		return country_code2;
	}

	public String getIsp() {
		return isp;
	}

	public String getResponse_code() {
		return response_code;
	}

	public String getResponse_message() {
		return response_message;
	}

	// Setter Methods

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setIp_number(String ip_number) {
		this.ip_number = ip_number;
	}

	public void setIp_version(float ip_version) {
		this.ip_version = ip_version;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public void setCountry_code2(String country_code2) {
		this.country_code2 = country_code2;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}
}
