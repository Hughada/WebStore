package com.hughadatips.repository;

import com.hughadatips.entity.Review;
import com.hughadatips.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTrip(Trip trip);
}
