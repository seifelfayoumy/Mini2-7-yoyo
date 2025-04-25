package com.example.miniapp.repositories;

import com.example.miniapp.models.Captain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaptainRepository extends JpaRepository<Captain, Long> {

    /**
     * Find all captains with an average rating score above the given threshold.
     */
    List<Captain> findByAvgRatingScoreGreaterThan(Double ratingThreshold);

    /**
     * Locate a captain by their license number.
     * @return Optional containing the captain if found
     */
    Optional<Captain> findByLicenseNumber(String licenseNumber);
}