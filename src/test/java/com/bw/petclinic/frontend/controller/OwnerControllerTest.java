package com.bw.petclinic.frontend.controller;

import com.bw.petclinic.frontend.domain.Owner;
import com.bw.petclinic.frontend.domain.Pet;
import com.bw.petclinic.frontend.domain.PetType;
import com.bw.petclinic.frontend.domain.Visit;
import com.bw.petclinic.frontend.service.OwnerService;
import com.bw.petclinic.frontend.service.PetService;
import com.bw.petclinic.frontend.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private OwnerService ownerService;
    @MockBean private PetService petService;
    @MockBean private VisitService visitService;

    private Owner newOwner;
    private Owner savedOwner;

    @BeforeEach
    public void setup() {
        newOwner = new Owner(0, "Jason", "Tan",
                "ADD 1 Martin Place", "Sydney", "0123456789");
        savedOwner = new Owner(1, "Jason", "Tan",
                "ADD 1 Martin Place", "Sydney", "0123456789");
    }

    @Test
    public void testOwnerDetails() throws Exception {
        when(ownerService.getById(1)).thenReturn(savedOwner);
        when(petService.getPets(1)).thenReturn(List.of(
                new Pet(1, "Leo",
                        LocalDate.of(2000, 9, 7),
                        new PetType(1, "Cat"), 1)));
        when(visitService.getVisits(1)).thenReturn(List.of(
                new Visit(1, LocalDate.of(2010, 3, 4), "rabies shot", 1)));
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/owners")
                        .param("ownerId", "1")
                        .with(user("user").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerDetails");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(1, owner.getId());
        assertFalse(owner.getPets().isEmpty());

        Pet pet = owner.getPets().get(0);
        assertNotNull(pet);
        assertEquals(1, pet.getId());
        assertEquals(1, pet.getOwnerId());
        assertFalse(pet.getVisits().isEmpty());

        Visit visit = pet.getVisits().get(0);
        assertNotNull(visit);
        assertEquals(1, visit.getId());
        assertEquals(1, visit.getPetId());
    }

    @Test
    public void testListOwnersEmpty() throws Exception {
        when(ownerService.findByLastName("abc", 0, 5)).thenReturn(
                new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 5), 0));
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/owners/list")
                        .param("page", "1")
                        .param("lastName", "abc")
                        .with(user("user").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerFind");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(0, owner.getId());

        BindingResult bindingResult = (BindingResult) modelAndView.getModel().get("org.springframework.validation.BindingResult.owner");
        assertNotNull(bindingResult);
        assertEquals(1, bindingResult.getErrorCount());

        FieldError error = bindingResult.getFieldError();
        assertNotNull(error);
        assertEquals("owner", error.getObjectName());
        assertEquals("lastName", error.getField());
        assertEquals("Not Found", error.getDefaultMessage());
    }

    @Test
    public void testListOwners() throws Exception {
        when(ownerService.findByLastName("", 0, 5)).thenReturn(new PageImpl<>(
                Arrays.asList(
                        new Owner(1, "Jason", "Tan",
                                "ADD 1 Martin Place", "Sydney", "0123456789"),
                        new Owner(2, "Jackson", "Tanner",
                                "ADD 2 Martin Place", "Canberra", "1123456789")),
                PageRequest.of(0, 5),
                12));
        when(petService.getPetNames(1)).thenReturn("Pet11, Pet12");
        when(petService.getPetNames(2)).thenReturn("Pet21, Pet22");
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/owners/list")
                        .param("page", "1")
                        .param("lastName", "")
                        .with(user("user").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerList");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(0, owner.getId());

        List<Owner> owners = (List<Owner>) modelAndView.getModel().get("owners");
        assertNotNull(owners);
        assertEquals(2, owners.size());
        assertEquals("Pet11, Pet12", owners.get(0).getPetNames());
        assertEquals("Pet21, Pet22", owners.get(1).getPetNames());

        assertEquals(1, modelAndView.getModel().get("currentPage"));
        assertEquals(3, modelAndView.getModel().get("totalPages"));
    }

    @Test
    public void testUpdateOwnerSuccess() throws Exception {
        when(ownerService.save(any(Owner.class))).thenReturn(savedOwner);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/owners/edit")
                        .param("ownerId", "1")
                        .param("firstName", newOwner.getFirstName())
                        .param("lastName", newOwner.getLastName())
                        .param("address", newOwner.getAddress())
                        .param("city", newOwner.getCity())
                        .param("telephone", newOwner.getTelephone())
                        .with(user("user").password("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/owners?ownerId=1");
    }

    @Test
    public void testEditOwner() throws Exception {
        when(ownerService.getById(1)).thenReturn(savedOwner);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/owners/edit")
                        .param("ownerId", "1")
                        .with(user("user").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(1, owner.getId());
        ModelAndViewAssert.assertViewName(modelAndView, "ownerForm");
    }

    @Test
    public void testAddOwnerTelephone10Digits() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/owners/new")
                        .param("firstName", newOwner.getFirstName())
                        .param("lastName", newOwner.getLastName())
                        .param("address", newOwner.getAddress())
                        .param("city", newOwner.getCity())
                        .param("telephone", "S123456789")
                        .with(user("user").password("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerForm");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(0, owner.getId());

        BindingResult bindingResult = (BindingResult) modelAndView.getModel().get("org.springframework.validation.BindingResult.owner");
        assertNotNull(bindingResult);
        assertEquals(1, bindingResult.getErrorCount());

        FieldError error = bindingResult.getFieldError();
        assertNotNull(error);
        assertEquals("owner", error.getObjectName());
        assertEquals("telephone", error.getField());
        assertEquals("Telephone must be 10 digits", error.getDefaultMessage());
    }

    @Test
    public void testAddOwnerCity4OrMoreChars() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/owners/new")
                        .param("firstName", newOwner.getFirstName())
                        .param("lastName", newOwner.getLastName())
                        .param("address", newOwner.getAddress())
                        .param("city", "Syd")
                        .param("telephone", newOwner.getTelephone())
                        .with(user("user").password("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerForm");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(0, owner.getId());

        BindingResult bindingResult = (BindingResult) modelAndView.getModel().get("org.springframework.validation.BindingResult.owner");
        assertNotNull(bindingResult);
        assertEquals(1, bindingResult.getErrorCount());

        FieldError error = bindingResult.getFieldError();
        assertNotNull(error);
        assertEquals("owner", error.getObjectName());
        assertEquals("city", error.getField());
        assertEquals("City must have at least 4 characters", error.getDefaultMessage());
    }

    @Test
    public void testAddOwnerAddressStartsWithADD() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/owners/new")
                        .param("firstName", newOwner.getFirstName())
                        .param("lastName", newOwner.getLastName())
                        .param("address", "1 Martin Place")
                        .param("city", newOwner.getCity())
                        .param("telephone", newOwner.getTelephone())
                        .with(user("user").password("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerForm");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(0, owner.getId());

        BindingResult bindingResult = (BindingResult) modelAndView.getModel().get("org.springframework.validation.BindingResult.owner");
        assertNotNull(bindingResult);
        assertEquals(1, bindingResult.getErrorCount());

        FieldError error = bindingResult.getFieldError();
        assertNotNull(error);
        assertEquals("owner", error.getObjectName());
        assertEquals("address", error.getField());
        assertEquals("Address must starts with ADD", error.getDefaultMessage());
    }

    @Test
    public void testAddOwnerBlankFirstName() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/owners/new")
                        .param("firstName", "")
                        .param("lastName", newOwner.getLastName())
                        .param("address", newOwner.getAddress())
                        .param("city", newOwner.getCity())
                        .param("telephone", newOwner.getTelephone())
                        .with(user("user").password("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerForm");

        Owner owner = (Owner) modelAndView.getModel().get("owner");
        assertNotNull(owner);
        assertEquals(0, owner.getId());

        BindingResult bindingResult = (BindingResult) modelAndView.getModel().get("org.springframework.validation.BindingResult.owner");
        assertNotNull(bindingResult);
        assertEquals(1, bindingResult.getErrorCount());

        FieldError error = bindingResult.getFieldError();
        assertNotNull(error);
        assertEquals("owner", error.getObjectName());
        assertEquals("firstName", error.getField());
        assertEquals("First Name is required", error.getDefaultMessage());
    }

    @Test
    public void testAddOwnerSuccess() throws Exception {
        when(ownerService.save(any(Owner.class))).thenReturn(savedOwner);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/owners/new")
                        .param("firstName", newOwner.getFirstName())
                        .param("lastName", newOwner.getLastName())
                        .param("address", newOwner.getAddress())
                        .param("city", newOwner.getCity())
                        .param("telephone", newOwner.getTelephone())
                        .with(user("user").password("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/owners?ownerId=1");
    }

    @Test
    public void testFindOwners() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/owners/find")
                        .with(user("user").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        ModelAndViewAssert.assertViewName(modelAndView, "ownerFind");
    }

    @Test
    public void testNewOwner() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/owners/new")
                        .with(user("user").password("user").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertTrue(modelAndView.getModel().containsKey("owner"));
        ModelAndViewAssert.assertViewName(modelAndView, "ownerForm");
    }

}
