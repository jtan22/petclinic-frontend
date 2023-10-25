package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.domain.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Value("${rest.service.pets.names}")
    private String restServicePetsNames;

    @Value("${rest.service.pets.owner}")
    private String restServicePetsOwner;

    @Value("${rest.service.pets.types}")
    private String restServicePetsTypes;

    @Value("${rest.service.pets.pet}")
    private String restServicePetsPet;

    @Value("${rest.service.pets.save}")
    private String restServicePetsSave;

    @Autowired
    private RestTemplate restTemplate;

    public String getPetNames(int ownerId) {
        return restTemplate.getForObject(String.format(restServicePetsNames, ownerId), String.class);
    }

    public List<Pet> getPets(int ownerId) {
        return restTemplate.exchange(String.format(restServicePetsOwner, ownerId), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pet>>() {}).getBody();
    }

    public List<PetType> getPetTypes() {
        return restTemplate.exchange(restServicePetsTypes, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PetType>>() {}).getBody();
    }

    public Pet getById(int id) {
        return restTemplate.getForObject(String.format(restServicePetsPet, id), Pet.class);
    }

    public Pet save(Pet pet) {
        return restTemplate.postForObject(restServicePetsSave, pet, Pet.class);
    }

}
