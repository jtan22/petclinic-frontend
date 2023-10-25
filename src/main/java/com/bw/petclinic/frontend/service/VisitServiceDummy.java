package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Visit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VisitServiceDummy implements VisitService {

    private static final Map<Integer, List<Visit>> VISITS = new HashMap<>();

    private static final AtomicInteger LAST_VISIT_ID = new AtomicInteger(4);

    public VisitServiceDummy() {
        VISITS.put(7, List.of(
                new Visit(1, LocalDate.of(2010, 3, 4), "rabies shot", 7),
                new Visit(4, LocalDate.of(2008, 9, 4), "spayed", 7)));
        VISITS.put(8, List.of(
                new Visit(2, LocalDate.of(2011, 3, 4), "rabies shot", 8),
                new Visit(3, LocalDate.of(2009, 6, 4), "neutered", 8)));
    }

    public List<Visit> getVisits(int petId) {
        return VISITS.containsKey(petId) ? VISITS.get(petId) : new ArrayList<>();
    }

    public void save(int petId, Visit visit) {
        visit.setId(LAST_VISIT_ID.incrementAndGet());
        visit.setPetId(petId);
        List<Visit> visits = new ArrayList<>();
        if (VISITS.containsKey(petId)) {
            visits.addAll(VISITS.get(petId));
        }
        visits.add(visit);
        VISITS.put(petId, visits);
    }

}
