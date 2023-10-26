package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.PetType;
import com.bw.petclinic.frontend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetService petService;

    private List<PetType> petTypes;

    @Autowired
    public PetTypeFormatter(PetService petService) {
        this.petService = petService;
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
        for (PetType type : getPetTypes()) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }

    public List<PetType> getPetTypes() {
        if (petTypes == null) {
            petTypes = petService.getPetTypes();
        }
        return petTypes;
    }

    public void setPetTypes(List<PetType> petTypes) {
        this.petTypes = petTypes;
    }
}
