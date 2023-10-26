package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
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
public class PetController {

    private static final Logger LOG = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private OwnerService ownerService;

    @Autowired
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

    @PostMapping("/pets/new")
    public String addPet(@RequestParam("ownerId") int ownerId, Pet pet, BindingResult bindingResult) {
        LOG.info("POST /pets/new with ownerId [" + ownerId + "]");
        if (bindingResult.hasErrors()) {
            return "petForm";
        }
        pet.setOwnerId(ownerId);
        petService.save(pet);
        return "redirect:/owners?ownerId=" + ownerId;
    }

    @PostMapping("/pets/edit")
    public String updatePet(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Pet pet,
                            BindingResult bindingResult) {
        LOG.info("POST /pets/edit with ownerId [" + ownerId + "]");
        if (bindingResult.hasErrors()) {
            return "petForm";
        }
        Pet existingPet = petService.getById(petId);
        pet.setId(existingPet.getId());
        pet.setVisits(existingPet.getVisits());
        pet.setOwnerId(ownerId);
        petService.save(pet);
        return "redirect:/owners?ownerId=" + ownerId;
    }

}
