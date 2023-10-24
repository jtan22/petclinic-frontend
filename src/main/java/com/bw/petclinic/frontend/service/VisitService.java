package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Visit;

import java.util.Set;

public interface VisitService {

    Set<Visit> getVisits(int petId);

    void save(int petId, Visit visit);

}
