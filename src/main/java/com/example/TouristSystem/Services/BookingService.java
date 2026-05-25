package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.BookingRepository;
import com.example.TouristSystem.Repository.TourpackageRepository;
import com.example.TouristSystem.Repository.UserRepository;
import com.example.TouristSystem.Repository.AccommodationRepository;
import com.example.TouristSystem.Models.Accommodation;
import com.example.TouristSystem.Models.Booking;
import com.example.TouristSystem.Models.Tourpackage;
import com.example.TouristSystem.Models.User;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourpackageRepository tourPackageRepository;
    private final AccommodationRepository accommodationRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          TourpackageRepository tourPackageRepository,
                          AccommodationRepository accommodationRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourPackageRepository = tourPackageRepository;
        this.accommodationRepository = accommodationRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking bookTourPackage(Long userId, Long packageId, int numberOfGuests,
                                    String checkInDate, String checkOutDate) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        Tourpackage tourPackage = tourPackageRepository.findById(packageId).orElse(null);
        if (tourPackage == null) throw new RuntimeException("Package not found: " + packageId);

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

    public Booking bookAccommodation(Long userId, Long accommodationId,
                                      String checkInDate, String checkOutDate,
                                      int numberOfGuests) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElse(null);
        if (accommodation == null) throw new RuntimeException("Accommodation not found: " + accommodationId);

        if (!accommodation.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Accommodation is not available");
        }

        Double totalCost = accommodation.getPrice() * numberOfGuests;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAccommodation(accommodation);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setNumberOfGuests(numberOfGuests);
        booking.setTotalCost(totalCost);
        booking.setStatus("PENDING");

        accommodation.setStatus("BOOKED");
        accommodationRepository.save(accommodation);

        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) throw new RuntimeException("Booking not found: " + bookingId);
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) throw new RuntimeException("Booking not found: " + bookingId);

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only cancel your own bookings");
        }

        if (booking.getAccommodation() != null) {
            Accommodation accommodation = booking.getAccommodation();
            accommodation.setStatus("AVAILABLE");
            accommodationRepository.save(accommodation);
        }

        if (booking.getTourPackage() != null) {
            Tourpackage tourPackage = booking.getTourPackage();
            tourPackage.setOpen_slots(tourPackage.getOpen_slots() + 1);
            tourPackageRepository.save(tourPackage);
        }

        bookingRepository.deleteById(bookingId);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}