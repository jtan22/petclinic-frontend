package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import com.bw.petclinic.frontend.service.VisitService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    private static final int PAGE_SIZE = 5;

    @Autowired
    @Qualifier("ownerServiceImpl")
    private OwnerService ownerService;

    @Autowired
    @Qualifier("petServiceImpl")
    private PetService petService;

    @Autowired
    @Qualifier("visitServiceImpl")
    private VisitService visitService;

    @GetMapping("/owners/find")
    public String findOwners(Model model) {
        LOG.info("Get /owners/find");
        model.addAttribute("owner", new Owner());
        return "ownerFind";
    }

    @GetMapping("/owners/new")
    public String newOwner(Model model) {
        LOG.info("Get /owners/new");
        model.addAttribute("owner", new Owner());
        return "ownerForm";
    }

    @PostMapping("/owners/new")
    public String addOwner(Owner owner, BindingResult bindingResult) {
        LOG.info("POST /owners/new");
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners?ownerId=" + savedOwner.getId();
    }

    @GetMapping("/owners/edit")
    public String editOwner(@RequestParam("ownerId") int ownerId, Model model) {
        LOG.info("Get /owners/edit?ownerId");
        model.addAttribute("owner", ownerService.getById(ownerId));
        return "ownerForm";
    }

    @PostMapping("/owners/edit")
    public String updateOwner(@RequestParam("ownerId") int ownerId, Owner owner, BindingResult bindingResult) {
        LOG.info("POST /owners/edit");
        if (bindingResult.hasErrors()) {
            return "ownerForm";
        }
        owner.setId(ownerId);
        ownerService.save(owner);
        return "redirect:/owners?ownerId=" + owner.getId();
    }

    @GetMapping("/owners/list")
    public String listOwners(@RequestParam(name = "page", defaultValue = "1") int page, Owner owner,
                             BindingResult bindingResult, Model model) {
        LOG.info("GET /owners/list?page");
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
        LOG.debug("Current page number = [" + page + "]");
        LOG.debug("Current page size = [" + owners.getContent().size() + "]");
        LOG.debug("Total page number = [" + owners.getTotalPages() + "]");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", owners.getTotalPages());
        model.addAttribute("owners", owners.getContent());
        return "ownerList";
    }

    @GetMapping("/owners")
    public String ownerDetails(@RequestParam("ownerId") int ownerId, Model model) {
        LOG.info("GET /owners?ownerId");
        Owner owner = ownerService.getById(ownerId);
        owner.setPets(petService.getPets(ownerId));
        for (Pet pet : owner.getPets()) {
            pet.setVisits(visitService.getVisits(pet.getId()));
        }
        model.addAttribute("owner", owner);
        return "ownerDetails";
    }
}
