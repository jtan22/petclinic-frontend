package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Pet;

import java.util.List;

public interface PetService {

    String getPetNames(int ownerId);

    List<Pet> getPets(int ownerId);

    List<String> getPetTypes();

    Pet getById(int id);

    void save(int ownerId, Pet pet);

}
