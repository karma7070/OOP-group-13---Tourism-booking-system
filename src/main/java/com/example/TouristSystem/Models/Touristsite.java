package com.example.TouristSystem.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tourist_sites")
public class Touristsite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;
    private Double pricePerPerson;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }
    
    public String getLocation() { return location; }
    
    public void setLocation(String location) { this.location = location; }
    
    public String getDescription() { return description; }
    
    public void setDescription(String description) { this.description = description; }
    
    public Double getPricePerPerson() { return pricePerPerson; }
    
    public void setPricePerPerson(Double pricePerPerson) { this.pricePerPerson = pricePerPerson; }
}
