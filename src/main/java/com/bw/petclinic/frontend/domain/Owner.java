package com.bw.petclinic.frontend.domain;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class Owner {

    private int id;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 4, message = "City must have at least 4 characters")
    private String city;

    @NotBlank(message = "Telephone is required")
    @Pattern(regexp = "[0-9]{10}", message = "Telephone must be 10 digits")
    private String telephone;

    private String petNames;

    private List<Pet> pets = new ArrayList<>();

    public Owner() {

    }

    public Owner(int id, String firstName, String lastName, String address, String city, String telephone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPetNames() {
        return petNames;
    }

    public void setPetNames(String petNames) {
        this.petNames = petNames;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", telephone='" + telephone + '\'' +
                ", petNames='" + petNames + '\'' +
                ", pets=" + pets +
                '}';
    }
}
