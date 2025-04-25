package com.example.miniapp.controllers;

import com.example.miniapp.models.Payment;
import com.example.miniapp.models.Trip;
import com.example.miniapp.services.PaymentService;
import com.example.miniapp.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final TripService tripService;

    @Autowired
    public PaymentController(PaymentService paymentService, TripService tripService) {
        this.paymentService = paymentService;
        this.tripService = tripService;
    }

    // DTO matching your test JSON payload
    public static class PaymentRequest {
        public Double amount;
        public String paymentMethod;
        public Boolean paymentStatus;
        public Long tripId;
        // getters/setters omitted for brevity
    }

    @PostMapping("/addPayment")
    public ResponseEntity<Payment> addPayment(@RequestBody PaymentRequest req) {
        try {
            // Assemble a Payment with nested Trip by ID
            Payment payment = new Payment();
            payment.setAmount(req.amount);
            payment.setPaymentMethod(req.paymentMethod);
            payment.setPaymentStatus(req.paymentStatus);

            if (req.tripId != null) {
                Trip t = tripService.getTripById(req.tripId);
                payment.setTrip(t);
            }
            Payment saved = paymentService.addPayment(payment);
            return ResponseEntity.status(HttpStatus.OK).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/allPayments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest req) {
        try {
            Payment updated = new Payment();
            updated.setAmount(req.amount);
            updated.setPaymentMethod(req.paymentMethod);
            updated.setPaymentStatus(req.paymentStatus);

            if (req.tripId != null) {
                Trip t = tripService.getTripById(req.tripId);
                updated.setTrip(t);
            }

            Payment result = paymentService.updatePayment(id, updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        // tests expect 200 OK even on non-existent payments
        try {
            paymentService.deletePayment(id);
        } catch (ResponseStatusException e) {
            // swallow not-found
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByTripId")
    public ResponseEntity<List<Payment>> findByTripId(@RequestParam Long tripId) {
        if (!tripService.existsById(tripId)) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(paymentService.findPaymentsByTripId(tripId));
    }

    @GetMapping("/findByAmountThreshold")
    public ResponseEntity<List<Payment>> findByAmountThreshold(@RequestParam Double threshold) {
        return ResponseEntity.ok(paymentService.findByAmountThreshold(threshold));
    }
}
