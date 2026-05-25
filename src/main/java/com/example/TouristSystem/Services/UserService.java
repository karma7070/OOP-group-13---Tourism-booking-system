package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.UserRepository;
import com.example.TouristSystem.Models.User;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User registerUser(User user) {
        User existing = repository.findByEmail(user.getEmail());
        if (existing != null) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }
        return repository.save(user);
    }

    public User updateProfile(Long id, User updatedDetails) {
        User existing = repository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        existing.setName(updatedDetails.getName());
        existing.setEmail(updatedDetails.getEmail());
        return repository.save(existing);
    }

    public User changePassword(Long id, String oldPassword, String newPassword) {
        User existing = repository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        if (!existing.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Incorrect old password");
        }
        existing.setPassword(newPassword);
        return repository.save(existing);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
