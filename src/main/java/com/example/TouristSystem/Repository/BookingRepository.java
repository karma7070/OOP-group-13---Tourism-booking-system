package com.example.TouristSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TouristSystem.Models.Booking;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
// This tells Spring to auto-generate: SELECT * FROM bookings WHERE user_id = ?
    List<Booking> findByUserId(Long userId);
}    