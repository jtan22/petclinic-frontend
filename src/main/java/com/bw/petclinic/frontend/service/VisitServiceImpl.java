package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Value("${rest.service.visits.pet}")
    private String restServiceVisitsPet;

    @Value("${rest.service.visits.save}")
    private String restServiceVisitsSave;

    @Autowired
    private RestTemplate restTemplate;

    public List<Visit> getVisits(int petId) {
        return restTemplate.exchange(String.format(restServiceVisitsPet, petId), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Visit>>() {}).getBody();
    }

    public Visit save(Visit visit) {
        return restTemplate.postForObject(restServiceVisitsSave, visit, Visit.class);
    }

}
