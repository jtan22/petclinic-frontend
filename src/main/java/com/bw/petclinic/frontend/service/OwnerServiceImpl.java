package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.CustomPageImpl;
import com.bw.petclinic.frontend.domain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<CustomPageImpl<Owner>>() {}).getBody();
    }

    public Owner getById(int id) {
        String uri = String.format(restServiceOwners + GET_OWNER_BY_ID, id);
        return restTemplate.getForObject(uri, Owner.class);
    }

    public Owner save(Owner owner) {
        return restTemplate.postForObject(restServiceOwners + SAVE_OWNER, owner, Owner.class);
    }

}
