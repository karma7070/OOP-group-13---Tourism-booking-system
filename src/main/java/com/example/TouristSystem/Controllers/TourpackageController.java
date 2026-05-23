package com.example.TouristSystem.Controllers;


import org.springframework.http.ResponseEntity;
import com.example.TouristSystem.Services.TourpackageService;
import com.example.TouristSystem.Models.Tourpackage;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sites")
public class TourpackageController {

    private final TourpackageService service;

    public TourpackageController(TourpackageService service) {
        this.service = service;
    }

    // GET http://localhost:8080/sites
    @GetMapping
    public List<Tourpackage> getAllSites() {
        return service.getAllSites();
    }

    // GET http://localhost:8080/sites/1
    @GetMapping("/{id}")
    public ResponseEntity<Tourpackage> getSiteById(@PathVariable Long id) {
        Tourpackage site = service.getSiteById(id);
        if (site == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(site);
    }

    // POST http://localhost:8080/sites
    @PostMapping
    public Tourpackage createSite(@RequestBody Tourpackage site) {
        return service.createSite(site);
    }

    // DELETE http://localhost:8080/sites/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        service.deleteSite(id);
        return ResponseEntity.ok().build();
    }
}