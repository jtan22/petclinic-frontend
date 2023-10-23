package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OwnerController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    @Qualifier("ownerServiceDummy")
    private OwnerService ownerService;

    @Autowired
    @Qualifier("petServiceDummy")
    private PetService petService;

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    @GetMapping("/owners/find")
    public String findOwners(Model model) {
        LOG.info("Get /owners/find");
        model.addAttribute("owner", new Owner());
        return "ownerFind";
    }

    @GetMapping("/owners/new")
    public String addOwner(Model model) {
        LOG.info("Get /owners/new");
        model.addAttribute("owner", new Owner());
        return "ownerForm";
    }

    @GetMapping("/owners/list")
    public String listOwners(@RequestParam(name = "page", defaultValue = "1") int page, Owner owner,
                             BindingResult bindingResult, Model model) {
        LOG.info("GET /owners/list");
        if (StringUtils.isBlank(owner.getLastName())) {
            owner.setLastName("");
        }
        Page<Owner> owners = ownerService.findByLastName(owner.getLastName(), PageRequest.of(page - 1, PAGE_SIZE));
        if (owners.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "Not Found");
            return "ownerFind";
        }
        for (Owner o : owners.getContent()) {
            o.setPets(petService.getPetNames(o.getId()));
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", owners.getTotalPages());
        model.addAttribute("owners", owners.getContent());
        return "ownerList";
    }
}
