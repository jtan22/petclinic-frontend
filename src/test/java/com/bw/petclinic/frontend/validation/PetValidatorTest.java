package com.bw.petclinic.frontend.validation;

import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.domain.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class PetValidatorTest {

    private PetValidator petValidator;

    private Pet pet;

    private Errors errors;

    @BeforeEach
    public void setup() {
        this.petValidator = new PetValidator();
        this.pet = new Pet(
                1,
                "Lucky",
                LocalDate.of(2023, 10, 30),
                new PetType(1, "cat"),
                1);
        this.errors = new DirectFieldBindingResult(pet, "pet");
    }

    @Test
    public void testValidateName() {
        pet.setName(null);
        petValidator.validate(pet, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getErrorCount(), 1);
        FieldError error = errors.getFieldError("name");
        assertNotNull(error);
        assertEquals(error.getCode(), "required");
        assertEquals(error.getDefaultMessage(), "is required");
    }

    @Test
    public void testValidateBirthDate() {
        pet.setBirthDate(null);
        petValidator.validate(pet, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getErrorCount(), 1);
        FieldError error = errors.getFieldError("birthDate");
        assertNotNull(error);
        assertEquals(error.getCode(), "required");
        assertEquals(error.getDefaultMessage(), "is required");
    }

    @Test
    public void testValidateNameAndBirthDate() {
        pet.setBirthDate(null);
        pet.setName(null);
        petValidator.validate(pet, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getErrorCount(), 2);
        assertNotNull(errors.getFieldError("name"));
        assertNotNull(errors.getFieldError("birthDate"));
    }

}
