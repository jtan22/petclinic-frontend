package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService{

    public Page<Owner> findByLastName(String lastName, PageRequest pageRequest) {
        return null;
    }

}
