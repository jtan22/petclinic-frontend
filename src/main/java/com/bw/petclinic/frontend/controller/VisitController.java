package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Visit;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import com.bw.petclinic.frontend.service.VisitService;
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
public class VisitController {

    private final OwnerService ownerService;

    private final PetService petService;

    private final VisitService visitService;

    @Autowired
    public VisitController(OwnerService ownerService, PetService petService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder()
    private void initBind(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/visits/new")
    public String newVisit(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, Model model) {
        model.addAttribute("owner", ownerService.getById(ownerId));
        model.addAttribute("pet", petService.getById(petId));
        model.addAttribute("visit", new Visit());
        return "visitForm";
    }

    @PostMapping("/visits/new")
    public String addVisit(@RequestParam("ownerId") int ownerId, @RequestParam("petId") int petId, @Valid Visit visit,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("owner", ownerService.getById(ownerId));
            model.addAttribute("pet", petService.getById(petId));
            return "visitForm";
        }
        visit.setPetId(petId);
        visitService.save(visit);
        return "redirect:/owners?ownerId=" + ownerId;
    }

}
