package com.bw.petclinic.frontend.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Visit {

    private int id;

    @NotNull(message = "Visit Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDate;

    @NotBlank(message = "Description is required")
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
