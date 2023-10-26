package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.domain.PagedOwners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Primary
public class OwnerServiceImpl implements OwnerService {

    private static final String FIND_OWNER_BY_LAST_NAME = "/owners/find?lastName=%s&pageNumber=%d&pageSize=%d";
    private static final String GET_OWNER_BY_ID = "/owners?ownerId=%d";
    private static final String SAVE_OWNER = "/owners";

    @Value("${rest.service.owners}")
    private String restServiceOwners;

    private final RestTemplate restTemplate;

    @Autowired
    public OwnerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<Owner> findByLastName(String lastName, int pageNumber, int pageSize) {
        String uri = String.format(restServiceOwners + FIND_OWNER_BY_LAST_NAME, lastName, pageNumber, pageSize);
        PagedOwners pagedOwners = restTemplate.getForObject(uri, PagedOwners.class);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (pagedOwners == null) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        return new PageImpl<>(pagedOwners.getOwners(), pageable, pagedOwners.getTotalOwners());
    }

    public Owner getById(int id) {
        String uri = String.format(restServiceOwners + GET_OWNER_BY_ID, id);
        return restTemplate.getForObject(uri, Owner.class);
    }

    public Owner save(Owner owner) {
        return restTemplate.postForObject(restServiceOwners + SAVE_OWNER, owner, Owner.class);
    }

}
