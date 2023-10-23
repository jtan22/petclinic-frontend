package com.bw.petclinic.frontend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PetServiceDummy implements PetService {

    private static final Map<Integer, String> PETS_NAMES = new HashMap<>();

    public PetServiceDummy() {
        PETS_NAMES.put(1, "Leo");
        PETS_NAMES.put(2, "Basil");
        PETS_NAMES.put(3, "Rosy, Jewel");
        PETS_NAMES.put(4, "Iggy");
        PETS_NAMES.put(5, "George");
        PETS_NAMES.put(6, "Samantha, Max");
        PETS_NAMES.put(7, "Lucky");
        PETS_NAMES.put(8, "Mulligan");
        PETS_NAMES.put(9, "Freddy");
        PETS_NAMES.put(10, "Lucky, Sly");
    }

    public String getPetNames(int ownerId) {
        return PETS_NAMES.get(ownerId);
    }

}
