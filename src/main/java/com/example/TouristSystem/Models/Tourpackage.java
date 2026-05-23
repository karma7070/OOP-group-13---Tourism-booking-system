package com.example.TouristSystem.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tourist_sites")
public class Tourpackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;
    private Double pricePerPerson;
    private String activities;
    private int open_slots;

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

    public String getActivities() { return activities; }

    public void setActivities(String activities) { this.activities = activities; }

    public int getOpen_slots() { return open_slots; }

    public void setOpen_slots(int open_slots) { this.open_slots = open_slots; }
}
