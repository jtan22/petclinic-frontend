package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.PagedVets;
import com.bw.petclinic.frontend.domain.Vet;
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
public class VetServiceImpl implements VetService {

    private static final String GET_PAGED_VETS = "/vets?pageNumber=%d&pageSize=%d";

    @Value("${rest.service.vets}")
    private String restServiceVets;

    @Autowired
    private RestTemplate restTemplate;

    public Page<Vet> getVets(int pageNumber, int pageSize) {
        String uri = String.format(restServiceVets + GET_PAGED_VETS, pageNumber, pageSize);
        PagedVets pagedVets = restTemplate.getForObject(uri, PagedVets.class);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (pagedVets == null) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        return new PageImpl<>(pagedVets.getVets(), pageable, pagedVets.getTotalVets());
    }

}
