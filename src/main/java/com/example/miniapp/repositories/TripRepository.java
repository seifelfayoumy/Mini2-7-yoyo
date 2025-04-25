package com.example.miniapp.repositories;

import com.example.miniapp.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    /**
     * Retrieve all trips between two dates.
     */
    List<Trip> findByTripDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieve all trips for a given captain ID.
     */
    List<Trip> findByCaptainId(Long captainId);
}
