package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import com.bw.petclinic.frontend.validation.PetValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetController {

    private final OwnerService ownerService;

    private final PetService petService;

    @Autowired
    public PetController(OwnerService ownerService, PetService petService) {
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @InitBinder("pet")
    private void initBind(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("/pets/new")
    public String newPet(@RequestParam("ownerId") int ownerId, Model model) {
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", new Pet());
        model.addAttribute("types", petService.getPetTypes());
        return "petForm";
    }

    @GetMapping("/pets/edit")
    public String editPet(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Model model) {
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", petService.getById(petId));
        model.addAttribute("types", petService.getPetTypes());
        return "petForm";
    }

    @PostMapping("/pets/new")
    public String addPet(@RequestParam("ownerId") int ownerId, @Valid Pet pet, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("owner", ownerService.getById(ownerId));
            model.addAttribute("types", petService.getPetTypes());
            return "petForm";
        }
        pet.setOwnerId(ownerId);
        petService.save(pet);
        return "redirect:/owners?ownerId=" + ownerId;
    }

    @PostMapping("/pets/edit")
    public String updatePet(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, @Valid Pet pet,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("owner", ownerService.getById(ownerId));
            model.addAttribute("types", petService.getPetTypes());
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
