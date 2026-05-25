package com.example.TouristSystem.Services;

import org.springframework.stereotype.Service;
import com.example.TouristSystem.Repository.PaymentRepository;
import com.example.TouristSystem.Repository.BookingRepository;
import com.example.TouristSystem.Models.Payment;
import com.example.TouristSystem.Models.Booking;
import com.example.TouristSystem.Models.PaymentMethod;
import com.example.TouristSystem.Models.PaymentStatus;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    public Payment makePayment(Long bookingId, PaymentMethod method) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) throw new RuntimeException("Booking not found: " + bookingId);

        Payment existing = paymentRepository.findByBookingId(bookingId);
        if (existing != null && existing.getStatus() == PaymentStatus.Paid) {
            throw new RuntimeException("Booking already paid");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setMethod(method);
        payment.setAmountPaid(booking.getTotalCost());
        payment.setStatus(PaymentStatus.Unpaid);
        payment.setPaymentDate(LocalDate.now());

        return paymentRepository.save(payment);
    }

    public Payment processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) throw new RuntimeException("Payment not found: " + paymentId);
        if (payment.getStatus() == PaymentStatus.Paid) {
            throw new RuntimeException("Payment already processed");
        }
        payment.setStatus(PaymentStatus.Paid);
        return paymentRepository.save(payment);
    }

    public Payment validatePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) throw new RuntimeException("Payment not found: " + paymentId);
        if (payment.getAmountPaid() == null || payment.getMethod() == null) {
            throw new RuntimeException("Payment is invalid — missing amount or method");
        }
        if (payment.getStatus() != PaymentStatus.Paid) {
            throw new RuntimeException("Payment has not been processed yet");
        }
        return payment;
    }

    public Payment refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) throw new RuntimeException("Payment not found: " + paymentId);
        if (payment.getStatus() != PaymentStatus.Paid) {
            throw new RuntimeException("Only paid payments can be refunded");
        }
        payment.setStatus(PaymentStatus.Refunded);
        return paymentRepository.save(payment);
    }

    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) throw new RuntimeException("Payment not found: " + paymentId);
        if (payment.getStatus() == PaymentStatus.Paid) {
            throw new RuntimeException("Cannot cancel a paid payment — use refund instead");
        }
        paymentRepository.deleteById(paymentId);
    }
}