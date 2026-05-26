package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.AccommodationRepository;
import com.example.TouristSystem.Repository.TourpackageRepository;
import com.example.TouristSystem.Models.Accommodation;
import com.example.TouristSystem.Models.Tourpackage;
import java.util.List;

@Service
public class SearchService {

    private final AccommodationRepository accommodationRepository;
    private final TourpackageRepository tourpackageRepository;

    public SearchService(AccommodationRepository accommodationRepository,
                         TourpackageRepository tourpackageRepository) {
        this.accommodationRepository = accommodationRepository;
        this.tourpackageRepository = tourpackageRepository;
    }

    public List<Accommodation> searchAccommodationByLocation(String location) {
        return accommodationRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Accommodation> searchAccommodationByType(String type) {
        return accommodationRepository.findByTypeContainingIgnoreCase(type);
    }

    public List<Accommodation> searchAvailableAccommodationByLocation(String location) {
        return accommodationRepository.findByStatusAndLocationContainingIgnoreCase("AVAILABLE", location);
    }

    public List<Tourpackage> searchTourPackageByLocation(String location) {
        return tourpackageRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Tourpackage> searchTourPackageByName(String name) {
        return tourpackageRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Tourpackage> searchTourPackageByMaxPrice(Double maxPrice) {
        return tourpackageRepository.findByPricePerPersonLessThanEqual(maxPrice);
    }
}