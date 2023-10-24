package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OwnerService {

    Page<Owner> findByLastName(String lastName, int pageNumber, int pageSize);

    Owner getById(int id);

    Owner save(Owner owner);

}
