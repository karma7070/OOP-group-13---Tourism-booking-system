package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.Touristsite;

@Repository
public interface TouristSiteRepository extends JpaRepository<Touristsite, Long> {

}