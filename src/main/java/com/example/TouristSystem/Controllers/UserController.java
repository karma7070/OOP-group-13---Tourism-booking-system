package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TouristSystem.Services.UserService;
import com.example.TouristSystem.Models.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User created = service.registerUser(user);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id,
                                               @RequestBody User updatedDetails) {
        User updated = service.updateProfile(id, updatedDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> changePassword(@PathVariable Long id,
                                                @RequestParam String oldPassword,
                                                @RequestParam String newPassword) {
        User updated = service.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(updated);
    }
}