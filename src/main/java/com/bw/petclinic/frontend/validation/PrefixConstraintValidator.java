package com.bw.petclinic.frontend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrefixConstraintValidator implements ConstraintValidator<Prefix, String> {

    private String prefix;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.startsWith(prefix);
    }

    @Override
    public void initialize(Prefix constraintAnnotation) {
        prefix = constraintAnnotation.value();
    }
}
