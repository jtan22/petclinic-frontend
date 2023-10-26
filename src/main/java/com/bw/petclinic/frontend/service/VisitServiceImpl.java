package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Primary
public class VisitServiceImpl implements VisitService {

    private static final String GET_VISITS_BY_PET_ID = "/visits/pet?petId=%d";
    private static final String SAVE_VISIT = "/visits/visit";

    @Value("${rest.service.visits}")
    private String restServiceVisits;

    @Autowired
    private RestTemplate restTemplate;

    public List<Visit> getVisits(int petId) {
        String uri = String.format(restServiceVisits + GET_VISITS_BY_PET_ID, petId);
        return restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Visit>>() {}).getBody();
    }

    public Visit save(Visit visit) {
        return restTemplate.postForObject(restServiceVisits + SAVE_VISIT, visit, Visit.class);
    }

}
