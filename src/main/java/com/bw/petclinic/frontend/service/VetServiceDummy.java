package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Specialty;
import com.bw.petclinic.frontend.domain.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VetServiceDummy implements VetService {

    private static final List<Vet> VETS = new ArrayList<>();

    public VetServiceDummy() {
        VETS.add(new Vet(1, "James", "Carter",
                new HashSet<>()));
        VETS.add(new Vet(2, "Helen", "Leary",
                Set.of(new Specialty(1, "radiology"))));
        VETS.add(new Vet(3, "Linda", "Douglas",
                Set.of(new Specialty(2, "surgery"), new Specialty(3, "dentistry"))));
        VETS.add(new Vet(4, "Rafael", "Ortega",
                Set.of(new Specialty(2, "surgery"))));
        VETS.add(new Vet(5, "Henry", "Stevens",
                Set.of(new Specialty(1, "radiology"))));
        VETS.add(new Vet(6, "Sharon", "Jenkins",
                new HashSet<>()));
    }

    public Page<Vet> getVets(int pageNumber, int pageSize) {
        List<Vet> vets = new ArrayList<>();
        int start = pageNumber * pageSize;
        int stop = (pageNumber + 1) * pageSize;
        if (start >= 0 && start <= VETS.size()) {
            for (int i = start; i < Math.min(stop, VETS.size()); i++) {
                vets.add(VETS.get(i));
            }
        }
        return new PageImpl<>(vets, PageRequest.of(pageNumber, pageSize), VETS.size());
    }

}
