package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.AccommodationRepository;
import com.example.TouristSystem.Repository.BookingRepository;
import com.example.TouristSystem.Repository.UserRepository;
import com.example.TouristSystem.Models.Accommodation;
import com.example.TouristSystem.Models.Booking;
import com.example.TouristSystem.Models.User;
import java.util.List;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public AccommodationService(AccommodationRepository accommodationRepository,
                                BookingRepository bookingRepository,
                                UserRepository userRepository) {
        this.accommodationRepository = accommodationRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }


    // Get all accommodations — admin sees everything
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    // Add a new accommodation — admin only
    public Accommodation addAccommodation(Accommodation accommodation) {
        accommodation.setStatus("AVAILABLE");
        return accommodationRepository.save(accommodation);
    }

    // Update accommodation details — admin only
    public Accommodation updateAccommodation(Long id, Accommodation updatedDetails) {
        Accommodation existing = accommodationRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Accommodation not found with id: " + id);
        }
        existing.setName(updatedDetails.getName());
        existing.setType(updatedDetails.getType());
        existing.setLocation(updatedDetails.getLocation());
        existing.setAmenities(updatedDetails.getAmenities());
        existing.setPrice(updatedDetails.getPrice());
        existing.setStatus(updatedDetails.getStatus());
        return accommodationRepository.save(existing);
    }

    // Update availability status — admin only
    public Accommodation updateAvailability(Long id, String status) {
        Accommodation accommodation = accommodationRepository.findById(id).orElse(null);
        if (accommodation == null) {
            throw new RuntimeException("Accommodation not found with id: " + id);
        }
        accommodation.setStatus(status);
        return accommodationRepository.save(accommodation);
    }

    // Delete an accommodation — admin only
    public void deleteAccommodation(Long id) {
        accommodationRepository.deleteById(id);
    }

    // Browse all available accommodations — users can see AVAILABLE ones only
    public List<Accommodation> getAvailableAccommodations() {
        return accommodationRepository.findByStatus("AVAILABLE");
    }

    // Get one accommodation by id — user viewing details
    public Accommodation getAccommodationById(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    // Book an accommodation — user action
    public Booking bookAccommodation(Long userId, Long accommodationId,
                                      String checkInDate, String checkOutDate,
                                      int numberOfGuests) {
        // Step 1 — find the user
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        // Step 2 — find the accommodation
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElse(null);
        if (accommodation == null) {
            throw new RuntimeException("Accommodation not found with id: " + accommodationId);
        }

        // Step 3 — check if accommodation is available
        if (!accommodation.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Accommodation is not available");
        }

        // Step 4 — calculate total cost
        Double totalCost = accommodation.getPrice() * numberOfGuests;

        // Step 5 — create the booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAccommodation(accommodation);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setNumberOfGuests(numberOfGuests);
        booking.setTotalCost(totalCost);
        booking.setStatus("PENDING");

        // Step 6 — mark accommodation as BOOKED
        accommodation.setStatus("BOOKED");
        accommodationRepository.save(accommodation);

        return bookingRepository.save(booking);
    }

    // Cancel/delete a booking — user action
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new RuntimeException("Booking not found with id: " + bookingId);
        }

        // Make sure the user owns this booking
        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only cancel your own bookings");
        }

        // Free up the accommodation again
        Accommodation accommodation = booking.getAccommodation();
        if (accommodation != null) {
            accommodation.setStatus("AVAILABLE");
            accommodationRepository.save(accommodation);
        }

        bookingRepository.deleteById(bookingId);
    }
}
