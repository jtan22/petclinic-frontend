package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    @GetMapping("/owners/find")
    public String findOwner(Model model) {
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

}
