package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Visit;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VisitController {

    @Autowired
    @Qualifier("ownerServiceDummy")
    private OwnerService ownerService;

    @Autowired
    @Qualifier("petServiceDummy")
    private PetService petService;

    private static final Logger LOG = LoggerFactory.getLogger(VisitController.class);

    @GetMapping("/visits/new")
    public String newVisit(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Model model) {
        LOG.info("GET /visits/new with ownerId [" + ownerId + "], petId [" + petId + "]");
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", petService.getById(petId));
        model.addAttribute("visit", new Visit());
        return "visitForm";
    }

}
