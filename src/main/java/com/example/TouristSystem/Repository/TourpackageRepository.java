package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.Tourpackage;

@Repository
public interface TourpackageRepository extends JpaRepository<Tourpackage, Long> {

}