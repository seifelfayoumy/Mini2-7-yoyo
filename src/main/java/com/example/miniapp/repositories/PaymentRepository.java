package com.example.miniapp.repositories;

import com.example.miniapp.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find payments by the associated trip ID.
     */
    List<Payment> findByTripId(Long tripId);

    /**
     * Find payments where the amount exceeds the specified threshold.
     */
    List<Payment> findByAmountGreaterThan(Double threshold);
}