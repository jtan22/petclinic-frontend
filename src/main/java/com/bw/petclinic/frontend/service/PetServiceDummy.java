package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Pet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetServiceDummy implements PetService {

    private static final Map<Integer, Set<Pet>> PETS = new HashMap<>();

    public PetServiceDummy() {
        PETS.put(1, Set.of(
                new Pet(1, "Leo", LocalDate.of(2000, 9, 7), "cat", 1)));
        PETS.put(2, Set.of(
                new Pet(2, "Basil", LocalDate.of(2002, 8, 6), "hamster", 2)));
        PETS.put(3, Set.of(
                new Pet(3, "Rosy", LocalDate.of(2001, 4, 17), "dog", 3),
                new Pet(4, "Jewel", LocalDate.of(2000, 3, 7), "dog", 3)));
        PETS.put(4, Set.of(
                new Pet(5, "Iggy", LocalDate.of(2000, 11, 30), "lizard", 4)));
        PETS.put(5, Set.of(
                new Pet(6, "George", LocalDate.of(2000, 1, 20), "snake", 5)));
        PETS.put(6, Set.of(
                new Pet(7, "Samantha", LocalDate.of(1995, 9, 4), "cat", 6),
                new Pet(8, "Max", LocalDate.of(1995, 9, 4), "dog", 6)));
        PETS.put(7, Set.of(
                new Pet(9, "Lucky", LocalDate.of(1999, 8, 6), "bird", 7)));
        PETS.put(8, Set.of(
                new Pet(10, "Mulligan", LocalDate.of(1997, 2, 24), "dog", 8)));
        PETS.put(9, Set.of(
                new Pet(11, "Freddy", LocalDate.of(2000, 3, 9), "bird", 9)));
        PETS.put(10, Set.of(
                new Pet(12, "Lucky", LocalDate.of(2000, 6, 24), "dog", 10),
                new Pet(13, "Sly", LocalDate.of(2002, 6, 8), "cat", 10)));
    }

    public String getPetNames(int ownerId) {
        return PETS.containsKey(ownerId) ?
                PETS.get(ownerId).stream().map(Pet::getName).collect(Collectors.joining(",")) :
                "";
    }

    public Set<Pet> getPets(int ownerId) {
        return PETS.containsKey(ownerId) ? PETS.get(ownerId) : new HashSet<>();
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

}
