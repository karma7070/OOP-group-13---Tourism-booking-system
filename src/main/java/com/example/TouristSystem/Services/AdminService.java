package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.UserRepository;
import com.example.TouristSystem.Repository.TourpackageRepository;
import com.example.TouristSystem.Repository.AccommodationRepository;
import com.example.TouristSystem.Models.User;
import com.example.TouristSystem.Models.Tourpackage;
import com.example.TouristSystem.Models.Accommodation;
import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final TourpackageRepository tourpackageRepository;
    private final AccommodationRepository accommodationRepository;

    public AdminService(UserRepository userRepository,
                        TourpackageRepository tourpackageRepository,
                        AccommodationRepository accommodationRepository) {
        this.userRepository = userRepository;
        this.tourpackageRepository = tourpackageRepository;
        this.accommodationRepository = accommodationRepository;
    }

    // User management
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Tour package management
    public List<Tourpackage> getAllPackages() {
        return tourpackageRepository.findAll();
    }

    public Tourpackage addPackage(Tourpackage tourpackage) {
        return tourpackageRepository.save(tourpackage);
    }

    public Tourpackage updatePackage(Long id, Tourpackage updatedDetails) {
        Tourpackage existing = tourpackageRepository.findById(id).orElse(null);
        if (existing == null) throw new RuntimeException("Package not found: " + id);
        existing.setName(updatedDetails.getName());
        existing.setLocation(updatedDetails.getLocation());
        existing.setDescription(updatedDetails.getDescription());
        existing.setPricePerPerson(updatedDetails.getPricePerPerson());
        existing.setActivities(updatedDetails.getActivities());
        existing.setOpen_slots(updatedDetails.getOpen_slots());
        return tourpackageRepository.save(existing);
    }

    public void deletePackage(Long id) {
        tourpackageRepository.deleteById(id);
    }

    // Accommodation management
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public Accommodation addAccommodation(Accommodation accommodation) {
        accommodation.setStatus("AVAILABLE");
        return accommodationRepository.save(accommodation);
    }

    public Accommodation updateAccommodation(Long id, Accommodation updatedDetails) {
        Accommodation existing = accommodationRepository.findById(id).orElse(null);
        if (existing == null) throw new RuntimeException("Accommodation not found: " + id);
        existing.setName(updatedDetails.getName());
        existing.setType(updatedDetails.getType());
        existing.setLocation(updatedDetails.getLocation());
        existing.setAmenities(updatedDetails.getAmenities());
        existing.setPrice(updatedDetails.getPrice());
        existing.setStatus(updatedDetails.getStatus());
        return accommodationRepository.save(existing);
    }

    public void deleteAccommodation(Long id) {
        accommodationRepository.deleteById(id);
    }
}