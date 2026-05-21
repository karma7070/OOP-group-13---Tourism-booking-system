package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
