package com.example.TouristSystem.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "accommodations")

public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;
    private String location;
    private String amenities;
    private double price;
    private String status;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
    this.id = id;   
    }

    public void setType(String type){
         this.type = type;
    }

    public String getType(){
        return type;
    }

        public void setName(String name){
         this.name = name;
    }

        public String getName(){
        return name;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setAmenities(String amenities){
        this.amenities = amenities;
    }

    public String getAmenities(){
        return amenities;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}
