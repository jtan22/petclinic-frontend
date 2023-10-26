package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.domain.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Primary
public class PetServiceImpl implements PetService {

    private static final String GET_PET_NAMES_BY_OWNER_ID = "/pets/names?ownerId=%d";
    private static final String GET_PETS_BY_OWNER_ID = "/pets/owner?ownerId=%d";
    private static final String GET_ALL_PET_TYPES = "/pets/types";
    private static final String GET_PET_BY_ID = "/pets/pet?id=%d";
    private static final String SAVE_PET = "/pets/pet";

    @Value("${rest.service.pets}")
    private String restServicePets;

    @Autowired
    private RestTemplate restTemplate;

    public String getPetNames(int ownerId) {
        String uri = String.format(restServicePets + GET_PET_NAMES_BY_OWNER_ID, ownerId);
        return restTemplate.getForObject(uri, String.class);
    }

    public List<Pet> getPets(int ownerId) {
        String uri = String.format(restServicePets + GET_PETS_BY_OWNER_ID, ownerId);
        return restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pet>>() {}).getBody();
    }

    public List<PetType> getPetTypes() {
        return restTemplate.exchange(restServicePets + GET_ALL_PET_TYPES, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PetType>>() {}).getBody();
    }

    public Pet getById(int id) {
        String uri = String.format(restServicePets + GET_PET_BY_ID, id);
        return restTemplate.getForObject(uri, Pet.class);
    }

    public Pet save(Pet pet) {
        return restTemplate.postForObject(restServicePets + SAVE_PET, pet, Pet.class);
    }

}
