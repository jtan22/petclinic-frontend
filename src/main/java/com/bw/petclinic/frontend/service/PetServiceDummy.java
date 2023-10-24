package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Pet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PetServiceDummy implements PetService {

    private static final Map<Integer, List<Pet>> PETS = new HashMap<>();

    private static final AtomicInteger LAST_PET_ID = new AtomicInteger(13);

    public PetServiceDummy() {
        PETS.put(1, List.of(
                new Pet(1, "Leo", LocalDate.of(2000, 9, 7), "Cat", 1)));
        PETS.put(2, List.of(
                new Pet(2, "Basil", LocalDate.of(2002, 8, 6), "Hamster", 2)));
        PETS.put(3, List.of(
                new Pet(3, "Rosy", LocalDate.of(2001, 4, 17), "Dog", 3),
                new Pet(4, "Jewel", LocalDate.of(2000, 3, 7), "Dog", 3)));
        PETS.put(4, List.of(
                new Pet(5, "Iggy", LocalDate.of(2000, 11, 30), "Lizard", 4)));
        PETS.put(5, List.of(
                new Pet(6, "George", LocalDate.of(2000, 1, 20), "Snake", 5)));
        PETS.put(6, List.of(
                new Pet(7, "Samantha", LocalDate.of(1995, 9, 4), "Cat", 6),
                new Pet(8, "Max", LocalDate.of(1995, 9, 4), "Dog", 6)));
        PETS.put(7, List.of(
                new Pet(9, "Lucky", LocalDate.of(1999, 8, 6), "Bird", 7)));
        PETS.put(8, List.of(
                new Pet(10, "Mulligan", LocalDate.of(1997, 2, 24), "Dog", 8)));
        PETS.put(9, List.of(
                new Pet(11, "Freddy", LocalDate.of(2000, 3, 9), "Bird", 9)));
        PETS.put(10, List.of(
                new Pet(12, "Lucky", LocalDate.of(2000, 6, 24), "Dog", 10),
                new Pet(13, "Sly", LocalDate.of(2002, 6, 8), "Cat", 10)));
    }

    public String getPetNames(int ownerId) {
        return PETS.containsKey(ownerId) ?
                PETS.get(ownerId).stream().map(Pet::getName).collect(Collectors.joining(",")) :
                "";
    }

    public List<Pet> getPets(int ownerId) {
        return PETS.containsKey(ownerId) ? PETS.get(ownerId) : new ArrayList<>();
    }

    public List<String> getPetTypes() {
        return List.of("Cat", "Dog", "Lizard", "Snake", "Bird", "Hamster");
    }

    public Pet getById(int id) {
        return PETS.values().stream()
                .flatMap(Collection::stream)
                .filter(pet -> pet.getId() == id)
                .findFirst()
                .orElseGet(Pet::new);
    }

    public void save(int ownerId, Pet pet) {
        if (pet.getId() == 0) {
            pet.setId(LAST_PET_ID.incrementAndGet());
            pet.setOwnerId(ownerId);
        }
        List<Pet> pets = new ArrayList<>();
        for (Pet p : PETS.get(ownerId)) {
            if (p.getId() != pet.getId()) {
                pets.add(p);
            }
        }
        pets.add(pet);
        PETS.put(ownerId, pets);
    }

}
