package com.example.TouristSystem.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone_number;
    private String nationality;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public void setName(String name){
         this.name = name;
    }

    public String getName(){
        return name;
    }

        public void setEmail(String email){
         this.email = email;
    }
    
    public String getEmail(){
        return email;
    }

            public void setPassword(String password){
         this.password = password;
    }
    
    public String getPassword(){
        return password;
    }

    public void setPhone_number(String phone_number){
        this.phone_number = phone_number;   
}

    public String getPhone_number(){
        return phone_number;
    }

    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public String getNationality(){
        return nationality;
    }

}

