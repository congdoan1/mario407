package edu.cs544.mario477.validation.Impl;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Validation;

public class AdvertisementValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
