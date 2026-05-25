package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TouristSystem.Services.AdminService;
import com.example.TouristSystem.Models.User;
import com.example.TouristSystem.Models.Tourpackage;
import com.example.TouristSystem.Models.Accommodation;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // User management
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = adminService.getUserById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Tour package management
    @GetMapping("/packages")
    public List<Tourpackage> getAllPackages() {
        return adminService.getAllPackages();
    }

    @PostMapping("/packages")
    public Tourpackage addPackage(@RequestBody Tourpackage tourpackage) {
        return adminService.addPackage(tourpackage);
    }

    @PutMapping("/packages/{id}")
    public ResponseEntity<Tourpackage> updatePackage(@PathVariable Long id,
                                                      @RequestBody Tourpackage updatedDetails) {
        Tourpackage updated = adminService.updatePackage(id, updatedDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/packages/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        adminService.deletePackage(id);
        return ResponseEntity.ok().build();
    }

    // Accommodation management
    @GetMapping("/accommodations")
    public List<Accommodation> getAllAccommodations() {
        return adminService.getAllAccommodations();
    }

    @PostMapping("/accommodations")
    public Accommodation addAccommodation(@RequestBody Accommodation accommodation) {
        return adminService.addAccommodation(accommodation);
    }

    @PutMapping("/accommodations/{id}")
    public ResponseEntity<Accommodation> updateAccommodation(@PathVariable Long id,
                                                              @RequestBody Accommodation updatedDetails) {
        Accommodation updated = adminService.updateAccommodation(id, updatedDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/accommodations/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        adminService.deleteAccommodation(id);
        return ResponseEntity.ok().build();
    }
}