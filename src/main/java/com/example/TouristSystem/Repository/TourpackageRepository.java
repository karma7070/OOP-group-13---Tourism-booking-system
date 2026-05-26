package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.Tourpackage;
import java.util.List;

@Repository
public interface TourpackageRepository extends JpaRepository<Tourpackage, Long> {
List<Tourpackage> findByLocationContainingIgnoreCase(String location);
List<Tourpackage> findByNameContainingIgnoreCase(String name);
List<Tourpackage> findByPricePerPersonLessThanEqual(Double maxPrice);
}