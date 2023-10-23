package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Pet;

import java.util.List;
import java.util.Set;

public interface PetService {

    String getPetNames(int ownerId);

    Set<Pet> getPets(int ownerId);

    List<String> getPetTypes();

}
