package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OwnerServiceDummy implements OwnerService {

    private static final Map<Integer, Owner> OWNERS = new HashMap<>();

    public OwnerServiceDummy() {
        OWNERS.put(1, new Owner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"));
        OWNERS.put(2, new Owner(2, "Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749"));
        OWNERS.put(3, new Owner(3, "Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763"));
        OWNERS.put(4, new Owner(4, "Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198"));
        OWNERS.put(5, new Owner(5, "Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765"));
        OWNERS.put(6, new Owner(6, "Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654"));
        OWNERS.put(7, new Owner(7, "Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387"));
        OWNERS.put(8, new Owner(8, "Maria", "Escobito", "345 Maple St.", "Madison", "6085557683"));
        OWNERS.put(9, new Owner(9, "David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435"));
        OWNERS.put(10, new Owner(10, "Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487"));
    }

    public Owner getById(int id) {
        return OWNERS.containsKey(id) ? OWNERS.get(id) : new Owner();
    }

    public Page<Owner> findByLastName(String lastName, PageRequest pageRequest) {
        List<Owner> owners = new ArrayList<>();
        List<Owner> matchedOwners = findMatchedOwners(lastName);
        if (matchedOwners.isEmpty()) {
            return new PageImpl<>(owners);
        }
        int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int stop = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();
        if (start >= 0 && start <= matchedOwners.size()) {
            for (int i = start; i < Math.min(stop, matchedOwners.size()); i++) {
                owners.add(matchedOwners.get(i));
            }
        }
        return new PageImpl<>(owners, pageRequest, matchedOwners.size());
    }

    private List<Owner> findMatchedOwners(String lastName) {
        if (StringUtils.isBlank(lastName)) {
            return OWNERS.values().stream().toList();
        }
        List<Owner> matchedOwners = new ArrayList<>();
        for (Owner owner : OWNERS.values()) {
            if (owner.getLastName().startsWith(lastName)) {
                matchedOwners.add(owner);
            }
        }
        return matchedOwners;
    }

}
