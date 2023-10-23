package com.bw.petclinic.frontend.domain;

public class Owner {

    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "lastName='" + lastName + '\'' +
                '}';
    }
}
