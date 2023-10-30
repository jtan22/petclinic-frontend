package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Vet;
import com.bw.petclinic.frontend.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VetController {

    private static final int PAGE_SIZE = 3;

    private final VetService vetService;

    @Autowired
    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/vets/list")
    public String listVets(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Page<Vet> vets = vetService.getVets(page - 1, PAGE_SIZE);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vets.getTotalPages());
        model.addAttribute("vets", vets.getContent());
        return "vetList";
    }

}
