package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Visit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VisitServiceDummy implements VisitService {

    private static final Map<Integer, Set<Visit>> VISITS = new HashMap<>();

    public VisitServiceDummy() {
        VISITS.put(7, Set.of(
                new Visit(1, LocalDate.of(2010, 3, 4), "rabies shot", 7),
                new Visit(4, LocalDate.of(2008, 9, 4), "spayed", 7)));
        VISITS.put(8, Set.of(
                new Visit(2, LocalDate.of(2011, 3, 4), "rabies shot", 8),
                new Visit(3, LocalDate.of(2009, 6, 4), "neutered", 8)));
    }

    public Set<Visit> getVisits(int petId) {
        return VISITS.containsKey(petId) ? VISITS.get(petId) : new HashSet<>();
    }

}