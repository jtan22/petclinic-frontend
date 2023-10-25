package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.domain.PetType;

import java.util.List;

public interface PetService {

    String getPetNames(int ownerId);

    List<Pet> getPets(int ownerId);

    List<PetType> getPetTypes();

    Pet getById(int id);

    Pet save(Pet pet);

}
