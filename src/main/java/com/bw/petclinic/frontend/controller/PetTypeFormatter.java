package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.PetType;
import com.bw.petclinic.frontend.service.PetService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private List<PetType> petTypes;

    public PetTypeFormatter(@Qualifier("petServiceImpl") PetService petService) {
        petTypes = petService.getPetTypes();
    }

    /**
     * Make sure PetType.toString() returns 'name' only.
     *
     * @param petType
     * @param locale
     * @return
     */
    @Override
    public String print(PetType petType, Locale locale) {
        return petType.toString();
    }

    /**
     * Make sure PetType.toString() returns 'name' only.
     *
     * @param text this is the PetType.toString()
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        for (PetType type : petTypes) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }

}
