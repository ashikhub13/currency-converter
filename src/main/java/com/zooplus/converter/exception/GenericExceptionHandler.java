package com.zooplus.converter.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IPNotFoundException.class)
	public ResponseEntity<Object> ipNotFoundException(IPNotFoundException exception,
			HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(IPMetadataLookupException.class)
	public ResponseEntity<Object> ipMetadataLookupException(IPMetadataLookupException exception,
			HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(CryptoPriceLookupException.class)
	public ResponseEntity<Object> cryptoPriceLookupException(CryptoPriceLookupException exception,
			HttpServletRequest httpServletRequest) {
		return new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE);
	}

}
