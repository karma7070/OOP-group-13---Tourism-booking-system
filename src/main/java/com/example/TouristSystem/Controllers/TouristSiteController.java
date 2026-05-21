package com.example.TouristSystem.Controllers;


import org.springframework.http.ResponseEntity;
import com.example.TouristSystem.Services.TouristSiteService;
import com.example.TouristSystem.Models.Touristsite;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sites")
public class TouristSiteController {

    private final TouristSiteService service;

    public TouristSiteController(TouristSiteService service) {
        this.service = service;
    }

    // GET http://localhost:8080/sites
    @GetMapping
    public List<Touristsite> getAllSites() {
        return service.getAllSites();
    }

    // GET http://localhost:8080/sites/1
    @GetMapping("/{id}")
    public ResponseEntity<Touristsite> getSiteById(@PathVariable Long id) {
        Touristsite site = service.getSiteById(id);
        if (site == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(site);
    }

    // POST http://localhost:8080/sites
    @PostMapping
    public Touristsite createSite(@RequestBody Touristsite site) {
        return service.createSite(site);
    }

    // DELETE http://localhost:8080/sites/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        service.deleteSite(id);
        return ResponseEntity.ok().build();
    }
}