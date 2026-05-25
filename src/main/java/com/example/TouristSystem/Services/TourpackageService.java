package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.TourpackageRepository;
import com.example.TouristSystem.Models.Tourpackage;
import java.util.List;

@Service
public class TourpackageService {

    private final TourpackageRepository repository;

    // Spring automatically injects the repository here
    public TourpackageService(TourpackageRepository repository) {
        this.repository = repository;
    }

    // Get all sites from the database
    public List<Tourpackage> getAllSites() {
        return repository.findAll();
    }

    // Get one site by its id
    public Tourpackage getSiteById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Save a new site to the database
    public Tourpackage createSite(Tourpackage site) {
        return repository.save(site);
    }

    // Delete a site by its id
    public void deleteSite(Long id) {
        repository.deleteById(id);
    }
}
