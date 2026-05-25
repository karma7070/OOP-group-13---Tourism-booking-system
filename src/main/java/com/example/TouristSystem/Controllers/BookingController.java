package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TouristSystem.Services.BookingService;
import com.example.TouristSystem.Models.Booking;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @PostMapping("/{userId}/{packageId}")
    public ResponseEntity<Booking> bookTourPackage(
            @PathVariable Long userId,
            @PathVariable Long packageId,
            @RequestParam int numberOfGuests,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {
        Booking booking = bookingService.bookTourPackage(
                userId, packageId, numberOfGuests, checkInDate, checkOutDate);
        if (booking == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long id) {
        Booking booking = bookingService.confirmBooking(id);
        if (booking == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/accommodation/{userId}/{accommodationId}")
    public ResponseEntity<Booking> bookAccommodation(
        @PathVariable Long userId,
        @PathVariable Long accommodationId,
        @RequestParam int numberOfGuests,
        @RequestParam String checkInDate,
        @RequestParam String checkOutDate) {
    Booking booking = bookingService.bookAccommodation(
            userId, accommodationId, checkInDate, checkOutDate, numberOfGuests);
    if (booking == null) return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(booking);
}

@PutMapping("/{bookingId}/cancel/{userId}")
public ResponseEntity<Void> cancelBooking(
        @PathVariable Long bookingId,
        @PathVariable Long userId) {
    bookingService.cancelBooking(bookingId, userId);
    return ResponseEntity.ok().build();
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
}