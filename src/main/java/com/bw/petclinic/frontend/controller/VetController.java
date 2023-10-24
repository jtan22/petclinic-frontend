package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Vet;
import com.bw.petclinic.frontend.service.VetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VetController {

    private static final Logger LOG = LoggerFactory.getLogger(VetController.class);
    private static final int PAGE_SIZE = 3;

    @Autowired
    @Qualifier("vetServiceDummy")
    private VetService vetService;

    @GetMapping("/vets/list")
    public String listVets(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        LOG.info("GET /vets/list");
        Page<Vet> vets = vetService.getVets(PageRequest.of(page - 1, PAGE_SIZE));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vets.getTotalPages());
        model.addAttribute("vets", vets.getContent());
        return "vetList";
    }

}
