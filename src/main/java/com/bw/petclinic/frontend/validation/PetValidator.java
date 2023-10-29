package com.bw.petclinic.frontend.validation;

import com.bw.petclinic.frontend.domain.Pet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PetValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pet.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pet pet = (Pet) target;
        if (StringUtils.isBlank(pet.getName())) {
            errors.rejectValue("name", "required", "is required");
        }
        if (pet.getBirthDate() == null) {
            errors.rejectValue("birthDate", "required", "is required");
        }
    }
}
