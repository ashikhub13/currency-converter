package com.zooplus.converter.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String DEFAULT_ERROR_VIEW = "converter";

	@ExceptionHandler(IPNotFoundException.class)
	public ModelAndView ipNotFoundException(IPNotFoundException exception, HttpServletRequest httpServletRequest) {
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception.getMessage());
		mav.addObject("url", httpServletRequest.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	@ExceptionHandler(IPMetadataLookupException.class)
	public ModelAndView ipMetadataLookupException(IPMetadataLookupException exception,
			HttpServletRequest httpServletRequest) {
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception.getMessage());
		mav.addObject("url", httpServletRequest.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	@ExceptionHandler(CryptoPriceLookupException.class)
	public ModelAndView cryptoPriceLookupException(CryptoPriceLookupException exception,
			HttpServletRequest httpServletRequest) {
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception.getMessage());
		mav.addObject("url", httpServletRequest.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	@ExceptionHandler(CountryCurrencyLookupException.class)
	public ModelAndView countryCurrencyLookupException(CountryCurrencyLookupException exception,
			HttpServletRequest httpServletRequest) {
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception.getMessage());
		mav.addObject("url", httpServletRequest.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

}
