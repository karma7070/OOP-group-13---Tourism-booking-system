package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TouristSystem.Services.PaymentService;
import com.example.TouristSystem.Models.Payment;
import com.example.TouristSystem.Models.PaymentMethod;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Payment> getPaymentByBookingId(@PathVariable Long bookingId) {
        Payment payment = paymentService.getPaymentByBookingId(bookingId);
        if (payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/make/{bookingId}")
    public ResponseEntity<Payment> makePayment(@PathVariable Long bookingId,
                                                @RequestParam PaymentMethod method) {
        Payment payment = paymentService.makePayment(bookingId, method);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<Payment> processPayment(@PathVariable Long id) {
        Payment payment = paymentService.processPayment(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<Payment> validatePayment(@PathVariable Long id) {
        Payment payment = paymentService.validatePayment(id);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long id) {
        Payment payment = paymentService.refundPayment(id);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.ok().build();
    }
}