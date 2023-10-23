package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OwnerService {

    Page<Owner> findByLastName(String lastName, PageRequest pageRequest);

    Owner getById(int id);

    void save(Owner owner);

}
