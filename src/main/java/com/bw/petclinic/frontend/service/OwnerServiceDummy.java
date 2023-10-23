package com.bw.petclinic.frontend.service;

import com.bw.petclinic.frontend.domain.Owner;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerServiceDummy implements OwnerService {

    private static final List<Owner> OWNERS = new ArrayList<>();

    public OwnerServiceDummy() {
        OWNERS.add(new Owner(1, "George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023"));
        OWNERS.add(new Owner(2, "Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749"));
        OWNERS.add(new Owner(3, "Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763"));
        OWNERS.add(new Owner(4, "Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198"));
        OWNERS.add(new Owner(5, "Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765"));
        OWNERS.add(new Owner(6, "Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654"));
        OWNERS.add(new Owner(7, "Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387"));
        OWNERS.add(new Owner(8, "Maria", "Escobito", "345 Maple St.", "Madison", "6085557683"));
        OWNERS.add(new Owner(9, "David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435"));
        OWNERS.add(new Owner(10, "Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487"));
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
            return OWNERS;
        }
        List<Owner> matchedOwners = new ArrayList<>();
        for (Owner owner : OWNERS) {
            if (owner.getLastName().startsWith(lastName)) {
                matchedOwners.add(owner);
            }
        }
        return matchedOwners;
    }

}
