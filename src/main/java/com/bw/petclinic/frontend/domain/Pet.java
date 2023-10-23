package com.bw.petclinic.frontend.domain;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class Pet {

    private int id;
    private String name;
    private LocalDate birthDate;
    private String type;
    private int ownerId;
    private Set<Visit> visits = new LinkedHashSet<>();

    public Pet() {

    }

    public Pet(int id, String name, LocalDate birthDate, String type, int ownerId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", type='" + type + '\'' +
                ", ownerId=" + ownerId +
                ", visits=" + visits +
                '}';
    }
}
