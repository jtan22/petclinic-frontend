package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Visit;

import java.util.List;

public interface VisitService {

    List<Visit> getVisits(int petId);

    Visit save(Visit visit);

}
