package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.BookingRepository;
import com.example.TouristSystem.Repository.TourpackageRepository;
import com.example.TouristSystem.Repository.UserRepository;
import com.example.TouristSystem.Models.Booking;
import com.example.TouristSystem.Models.Tourpackage;
import com.example.TouristSystem.Models.User;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourpackageRepository tourPackageRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          TourpackageRepository tourPackageRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get one booking by id
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Get all bookings for a specific user
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // Book a tour package — main booking logic
    public Booking bookTourPackage(Long userId, Long packageId, int numberOfGuests,
                                    String checkInDate, String checkOutDate) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        Tourpackage tourPackage = tourPackageRepository.findById(packageId).orElse(null);
        if (tourPackage == null) throw new RuntimeException("Package not found: " + packageId);

        // Calculate total automatically
        Double totalCost = tourPackage.getPricePerPerson() * numberOfGuests;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTourPackage(tourPackage);
        booking.setNumberOfGuests(numberOfGuests);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalCost(totalCost);
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    // Confirm a booking
    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) throw new RuntimeException("Booking not found: " + bookingId);
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    // Cancel a booking
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) throw new RuntimeException("Booking not found: " + bookingId);
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    // Delete a booking
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
