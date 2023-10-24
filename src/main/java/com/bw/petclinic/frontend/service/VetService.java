package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface VetService {

    Page<Vet> getVets(PageRequest pageRequest);

}
