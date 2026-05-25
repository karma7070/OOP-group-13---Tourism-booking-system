package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.User;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByRole(String role);
}
