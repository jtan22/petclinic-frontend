package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import com.bw.petclinic.frontend.service.VisitService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private static final int PAGE_SIZE = 5;

    private final OwnerService ownerService;

    private final PetService petService;

    private final VisitService visitService;

    @Autowired
    public OwnerController(OwnerService ownerService, PetService petService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder()
    private void initBind(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/owners/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner());
        return "ownerFind";
    }

    @GetMapping("/owners/new")
    public String newOwner(Model model) {
        model.addAttribute("owner", new Owner());
        return "ownerForm";
    }

    @PostMapping("/owners/new")
    public String addOwner(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ownerForm";
        }
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners?ownerId=" + savedOwner.getId();
    }

    @GetMapping("/owners/edit")
    public String editOwner(@RequestParam("ownerId") int ownerId, Model model) {
        model.addAttribute("owner", ownerService.getById(ownerId));
        return "ownerForm";
    }

    @PostMapping("/owners/edit")
    public String updateOwner(@RequestParam("ownerId") int ownerId, @Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            owner.setId(ownerId);
            return "ownerForm";
        }
        owner.setId(ownerId);
        ownerService.save(owner);
        return "redirect:/owners?ownerId=" + owner.getId();
    }

    @GetMapping("/owners/list")
    public String listOwners(@RequestParam(name = "page", defaultValue = "1") int page, Owner owner,
                             BindingResult bindingResult, Model model) {
        if (StringUtils.isBlank(owner.getLastName())) {
            owner.setLastName("");
        }
        Page<Owner> owners = ownerService.findByLastName(owner.getLastName(), page - 1, PAGE_SIZE);
        if (owners.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "Not Found");
            return "ownerFind";
        }
        for (Owner o : owners.getContent()) {
            o.setPetNames(petService.getPetNames(o.getId()));
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", owners.getTotalPages());
        model.addAttribute("owners", owners.getContent());
        return "ownerList";
    }

    @GetMapping("/owners")
    public String ownerDetails(@RequestParam("ownerId") int ownerId, Model model) {
        Owner owner = ownerService.getById(ownerId);
        owner.setPets(petService.getPets(ownerId));
        for (Pet pet : owner.getPets()) {
            pet.setVisits(visitService.getVisits(pet.getId()));
        }
        model.addAttribute("owner", owner);
        return "ownerDetails";
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Constructed OwnerController [" + this + "]");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("Destroying OwnerController [" + this + "]");
    }
}
