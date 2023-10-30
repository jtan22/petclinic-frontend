package com.bw.petclinic.frontend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "login");
    }

}
