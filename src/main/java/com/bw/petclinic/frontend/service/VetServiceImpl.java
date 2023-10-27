package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Vet;
import com.bw.petclinic.frontend.domain.CustomPageImpl;
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
public class VetServiceImpl implements VetService {

    private static final String GET_PAGED_VETS = "/vets?pageNumber=%d&pageSize=%d";

    @Value("${rest.service.vets}")
    private String restServiceVets;

    private final RestTemplate restTemplate;

    @Autowired
    public VetServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<Vet> getVets(int pageNumber, int pageSize) {
        String uri = String.format(restServiceVets + GET_PAGED_VETS, pageNumber, pageSize);
        return restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<CustomPageImpl<Vet>>() {}).getBody();
    }

}
