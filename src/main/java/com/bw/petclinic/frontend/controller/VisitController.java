package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Visit;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import com.bw.petclinic.frontend.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VisitController {

    private static final Logger LOG = LoggerFactory.getLogger(VisitController.class);

    private final OwnerService ownerService;

    private final PetService petService;

    private final VisitService visitService;

    @Autowired
    public VisitController(OwnerService ownerService, PetService petService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.visitService = visitService;
    }

    @GetMapping("/visits/new")
    public String newVisit(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Model model) {
        LOG.info("GET /visits/new with ownerId [" + ownerId + "], petId [" + petId + "]");
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", petService.getById(petId));
        model.addAttribute("visit", new Visit());
        return "visitForm";
    }

    @PostMapping("/visits/new")
    public String addVisit(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Visit visit,
                           BindingResult bindingResult) {
        LOG.info("POST /visits/new with ownerId [" + ownerId + "], petId [" + petId + "]");
        if (bindingResult.hasErrors()) {
            return "visitForm";
        }
        visit.setPetId(petId);
        visitService.save(visit);
        return "redirect:/owners?ownerId=" + ownerId;
    }

}
