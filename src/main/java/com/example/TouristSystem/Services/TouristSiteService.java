package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.TouristSiteRepository;
import com.example.TouristSystem.Models.Touristsite;
import java.util.List;

@Service
public class TouristSiteService {

    private final TouristSiteRepository repository;

    // Spring automatically injects the repository here
    public TouristSiteService(TouristSiteRepository repository) {
        this.repository = repository;
    }

    // Get all sites from the database
    public List<Touristsite> getAllSites() {
        return repository.findAll();
    }

    // Get one site by its id
    public Touristsite getSiteById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Save a new site to the database
    public Touristsite createSite(Touristsite site) {
        return repository.save(site);
    }

    // Delete a site by its id
    public void deleteSite(Long id) {
        repository.deleteById(id);
    }
}
