package com.example.miniapp.repositories;

import com.example.miniapp.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    /**
     * Find ratings for a given entity ID and type.
     */
    List<Rating> findByEntityIdAndEntityType(Long entityId, String entityType);

    /**
     * Retrieve ratings with a score greater than the specified minimum.
     */
    List<Rating> findByScoreGreaterThan(int minScore);
}
