package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Pet;
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
public class PetController {

    private static final Logger LOG = LoggerFactory.getLogger(PetController.class);

    @Autowired
    @Qualifier("ownerServiceDummy")
    private OwnerService ownerService;

    @Autowired
    @Qualifier("petServiceDummy")
    private PetService petService;

    @GetMapping("/pets/new")
    public String newPet(@RequestParam("ownerId") int ownerId, Model model) {
        LOG.info("GET /pets/new with ownerId [" + ownerId + "]");
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", new Pet());
        model.addAttribute("types", petService.getPetTypes());
        return "petForm";
    }

    @GetMapping("/pets/edit")
    public String editPet(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Model model) {
        LOG.info("GET /pets/edit with ownerId [" + ownerId + "], petId [" + petId + "]");
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", petService.getById(petId));
        model.addAttribute("types", petService.getPetTypes());
        return "petForm";
    }

}
