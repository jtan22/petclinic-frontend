package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VetServiceImpl implements VetService {

    public Page<Vet> getVets(PageRequest pageRequest) {
        return null;
    }

}
