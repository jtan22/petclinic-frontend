package com.bw.petclinic.frontend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = PrefixConstraintValidator.class)
public @interface Prefix {

    public String value();

    public String message();

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
