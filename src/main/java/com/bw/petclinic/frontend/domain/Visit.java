package com.bw.petclinic.frontend.domain;

import java.time.LocalDate;

public class Visit {

    private int id;
    private LocalDate visitDate;
    private String description;
    private int petId;

    public Visit() {

    }

    public Visit(int id, LocalDate visitDate, String description, int petId) {
        this.id = id;
        this.visitDate = visitDate;
        this.description = description;
        this.petId = petId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", visitDate=" + visitDate +
                ", description='" + description + '\'' +
                ", petId=" + petId +
                '}';
    }
}
