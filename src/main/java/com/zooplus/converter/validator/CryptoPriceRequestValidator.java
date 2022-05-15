package com.zooplus.converter.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class CryptoPriceRequestValidator implements ConstraintValidator<ValidCryptoPriceRequest, String> {

	public static final Pattern VALID_IP_ADDRESS_REGEX = Pattern.compile(
			"^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

	@Override
	public void initialize(ValidCryptoPriceRequest constraintAnnotation) {
	}

	/**
	 * Check if IP address matches regex
	 */
	@Override
	public boolean isValid(String ipAddress, ConstraintValidatorContext context) {
		if (StringUtils.isNotEmpty(ipAddress)) {
			Matcher matcher = VALID_IP_ADDRESS_REGEX.matcher(ipAddress);
			return matcher.find();
		}
		return true;
	}
}
