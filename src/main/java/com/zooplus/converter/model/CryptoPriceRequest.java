package com.zooplus.converter.model;

import javax.validation.constraints.NotEmpty;

import com.zooplus.converter.validator.ValidCryptoPriceRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPriceRequest {

	@NotEmpty(message = "Please choose a Cryptocurrency")
	private String code;

	@ValidCryptoPriceRequest
	private String address;

}
