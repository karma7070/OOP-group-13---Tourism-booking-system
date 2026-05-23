package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import com.example.TouristSystem.Models.Booking;
import com.example.TouristSystem.Services.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")

public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service){
        this.service = service;
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return service.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){   
        Booking booking = service.getBookingById(id);
        if(booking == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return service.getBookingsByUser(userId);
    }           






}
