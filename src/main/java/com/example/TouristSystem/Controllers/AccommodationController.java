package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TouristSystem.Services.AccommodationService;
import com.example.TouristSystem.Models.Accommodation;
import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public List<Accommodation> getAllAccommodations() {
        return accommodationService.getAllAccommodations();
    }

    @GetMapping("/available")
    public List<Accommodation> getAvailableAccommodations() {
        return accommodationService.getAvailableAccommodations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> getAccommodationById(@PathVariable Long id) {
        Accommodation accommodation = accommodationService.getAccommodationById(id);
        if (accommodation == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(accommodation);
    }

}
    

