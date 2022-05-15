package com.zooplus.converter.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *Interface class to validate for if IP address passed is valid
 *
 */
@Constraint(validatedBy = { CryptoPriceRequestValidator.class })
// This constraint annotation can be used only on fields and method parameters.
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ValidCryptoPriceRequest {

    String message() default "Please enter a valid IP address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}