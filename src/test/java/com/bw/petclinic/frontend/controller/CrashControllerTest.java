package com.bw.petclinic.frontend.controller;

import jakarta.servlet.ServletException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrashControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAccessDenied() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/access-denied"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        assertNotNull(mav);
        ModelAndViewAssert.assertViewName(mav, "access-denied");
    }

    @Test
    public void testError() {
        assertThrows(ServletException.class,
                () -> mockMvc.perform(MockMvcRequestBuilders.get("/oops")).andReturn());
    }

}
