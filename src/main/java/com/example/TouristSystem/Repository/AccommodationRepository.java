package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.Accommodation;
import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    // finds all accommodations where status = "AVAILABLE"
    List<Accommodation> findByStatus(String status);
    List<Accommodation> findByLocationContainingIgnoreCase(String location);
List<Accommodation> findByTypeContainingIgnoreCase(String type);
List<Accommodation> findByStatusAndLocationContainingIgnoreCase(String status, String location);
}
