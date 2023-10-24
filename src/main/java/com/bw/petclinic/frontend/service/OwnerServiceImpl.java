package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.domain.PagedOwners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class OwnerServiceImpl implements OwnerService{

    private static final Logger LOG = LoggerFactory.getLogger(OwnerServiceImpl.class);

    @Value("${rest.service.owners.find}")
    private String restServiceOwnersFind;

    @Value("${rest.service.owners.get}")
    private String restServiceOwnersGet;

    @Value("${rest.service.owners.save}")
    private String restServiceOwnersSave;

    @Autowired
    private RestTemplate restTemplate;

    public Page<Owner> findByLastName(String lastName, int pageNumber, int pageSize) {
        String uri = String.format(restServiceOwnersFind, lastName, pageNumber, pageSize);
        PagedOwners pagedOwners = restTemplate.getForObject(uri, PagedOwners.class);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (pagedOwners == null) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        return new PageImpl<>(pagedOwners.getOwners(), pageable, pagedOwners.getTotalOwners());
    }

    public Owner getById(int id) {
        return restTemplate.getForObject(String.format(restServiceOwnersGet, id), Owner.class);
    }

    public Owner save(Owner owner) {
        Owner savedOwner = restTemplate.postForObject(restServiceOwnersSave, owner, Owner.class);
        LOG.info("Saved Owner [" + savedOwner + "]");
        return savedOwner;
    }

}
