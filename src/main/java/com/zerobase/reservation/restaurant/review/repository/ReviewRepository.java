package com.zerobase.reservation.restaurant.review.repository;

import com.zerobase.reservation.restaurant.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
