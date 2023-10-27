package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Vet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Primary
public class VetServiceImpl implements VetService {

    private static final String GET_HAL_PAGED_VETS = "/data-rest/vets?page=%d&size=%d";

    @Value("${rest.service.vets}")
    private String restServiceVets;

    private final RestTemplate restTemplate;

    @Autowired
    public VetServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<Vet> getVets(int pageNumber, int pageSize) {
        String halUri = String.format(restServiceVets + GET_HAL_PAGED_VETS, pageNumber, pageSize);
        PagedModel<Vet> pagedVets = restTemplate.exchange(halUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<Vet>>() {}).getBody();
        if (pagedVets == null || pagedVets.getMetadata() == null) {
            throw new RuntimeException("Get Vets [" + pageNumber + "/" + pageSize + "] not found");
        }
        List<Vet> vets = List.copyOf(pagedVets.getContent());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        long totalElements = pagedVets.getMetadata().getTotalElements();
        return new PageImpl<>(vets, pageable, totalElements);
    }

}
